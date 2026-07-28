import { useState, useEffect } from "react";
import { useParams, useNavigate, Link } from "react-router";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { useQuery } from "@tanstack/react-query";
import api from "../services/api";
import type { Category, Subcategory, AdResponse, AdType } from "../types";
import Input from "../components/ui/Input";
import Textarea from "../components/ui/Textarea";
import Select from "../components/ui/Select";
import Button from "../components/ui/Button";
import Card from "../components/ui/Card";
import AdFilterPanel from "../components/ads/AdFilterPanel";
import { CardSkeleton } from "../components/ui/Skeleton";
import { toast } from "sonner";
import { Edit3, ArrowLeft, Save, Info } from "lucide-react";

const editAdSchema = z.object({
  adType: z.enum([
    "HOUSING",
    "FOR_SALE",
    "JOBS",
    "SERVICES",
    "GIGS",
    "RESUMES",
    "COMMUNITY",
    "EVENTS",
  ] as const, { error: "Category is required" }),
  subcategoryId: z.string().min(1, "Subcategory is required"),
  title: z.string().min(5, "Title must be at least 5 characters").max(100),
  description: z.string().min(15, "Description must be at least 15 characters"),
  price: z
    .string()
    .optional()
    .refine(
      (val) => !val || /^\d+(\.\d{1,2})?$/.test(val),
      "Price must be a valid number (e.g. 10 or 10.50)"
    ),
  location: z.string().min(3, "Location is required"),
  isFree: z.boolean().optional(),
  deliveryAvailable: z.boolean().optional(),

  // Category specific fields
  numberOfBeds: z.number().optional(),
  numberOfBathrooms: z.number().optional(),
  catsOk: z.boolean().optional(),
  dogsOk: z.boolean().optional(),
  furnished: z.boolean().optional(),
  housingType: z.string().optional(),
  rentPeriod: z.string().optional(),
  laundry: z.string().optional(),
  parking: z.string().optional(),

  soldBy: z.string().optional(),
  condition: z.string().optional(),

  employmentType: z.string().optional(),
  nonProfitOrganization: z.boolean().optional(),
  internship: z.boolean().optional(),
  telecommutingOk: z.boolean().optional(),

  serviceType: z.string().optional(),

  gigs: z.string().optional(),
  status: z.string().optional(),

  communityType: z.string().optional(),
  lostOrFound: z.string().optional(),

  educationCompleted: z.string().optional(),
  availableMornings: z.boolean().optional(),
  availableAfternoons: z.boolean().optional(),
  availableEvenings: z.boolean().optional(),
  availableOvernights: z.boolean().optional(),
  availableWeekdays: z.boolean().optional(),
  availableWeekends: z.boolean().optional(),
  hasImage: z.boolean().optional(),
});

type EditAdFormValues = z.infer<typeof editAdSchema>;

export default function EditAdPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [submitting, setSubmitting] = useState(false);

  // 1. Fetch Categories
  const { data: categories } = useQuery<Category[]>({
    queryKey: ["categories"],
    queryFn: async () => {
      const res = await api.get("/categories");
      return res.data;
    },
  });

  // 2. Fetch Existing Ad Details
  const { data: adDetails, isLoading: adLoading, error: adError } = useQuery<AdResponse & Record<string, any>>({
    queryKey: ["adDetail", id],
    enabled: !!id,
    queryFn: async () => {
      console.log(`[EditAdPage] Fetching ad details for ID=${id}`);
      const res = await api.get("/ads");
      const found = res.data?.find((item: any) => String(item.id) === String(id));
      if (!found) throw new Error("Listing not found");
      return found;
    },
  });

  const {
    register,
    handleSubmit,
    watch,
    reset,
    setValue,
    formState: { errors },
  } = useForm<EditAdFormValues>({
    resolver: zodResolver(editAdSchema),
  });

  const selectedCategory = watch("adType");

  // Find category object matching selected category string
  const activeCategoryObject = categories?.find(
    (c) => c.name.toUpperCase().replace(" ", "_") === selectedCategory
  );

  // 3. Fetch Subcategories for active category
  const { data: subcategories } = useQuery<Subcategory[]>({
    queryKey: ["subcategories", activeCategoryObject?.id],
    enabled: !!activeCategoryObject,
    queryFn: async () => {
      const res = await api.get(`/subcategories/category/${activeCategoryObject?.id}`);
      return res.data;
    },
  });

  // Populate form with existing ad data upon fetch
  useEffect(() => {
    if (adDetails && categories) {
      console.log("[EditAdPage] Populating existing ad data into form:", adDetails);
      
      // Infer category from subcategoryName if adType is not directly present
      let matchingCatName: AdType = "FOR_SALE";
      if (adDetails.adType) {
        matchingCatName = adDetails.adType as AdType;
      }

      reset({
        adType: matchingCatName,
        title: adDetails.title || "",
        description: adDetails.description || "",
        price: adDetails.price !== null && adDetails.price !== undefined ? String(adDetails.price) : "",
        location: adDetails.location || "",
        isFree: adDetails.price === 0 || adDetails.isFree || false,
        deliveryAvailable: adDetails.deliveryAvailable || false,
        subcategoryId: adDetails.subcategoryId ? String(adDetails.subcategoryId) : "",

        numberOfBeds: adDetails.numberOfBeds,
        numberOfBathrooms: adDetails.numberOfBathrooms,
        catsOk: adDetails.catsOk,
        dogsOk: adDetails.dogsOk,
        furnished: adDetails.furnished,
        housingType: adDetails.housingType,
        rentPeriod: adDetails.rentPeriod,
        laundry: adDetails.laundry,
        parking: adDetails.parking,

        soldBy: adDetails.soldBy,
        condition: adDetails.condition,

        employmentType: adDetails.employmentType,
        nonProfitOrganization: adDetails.nonProfitOrganization,
        internship: adDetails.internship,
        telecommutingOk: adDetails.telecommutingOk,

        serviceType: adDetails.serviceType,

        gigs: adDetails.gigs,
        status: adDetails.status,

        communityType: adDetails.communityType,
        lostOrFound: adDetails.lostOrFound,

        educationCompleted: adDetails.educationCompleted,
        availableMornings: adDetails.availableMornings,
        availableAfternoons: adDetails.availableAfternoons,
        availableEvenings: adDetails.availableEvenings,
        availableOvernights: adDetails.availableOvernights,
        availableWeekdays: adDetails.availableWeekdays,
        availableWeekends: adDetails.availableWeekends,
        hasImage: adDetails.hasImage,
      });
    }
  }, [adDetails, categories, reset]);

  const onSubmit = async (data: EditAdFormValues) => {
    console.log(`[EditAdPage] Submitting update for listing ID=${id}:`, data);
    setSubmitting(true);
    try {
      const cleanedData: Record<string, any> = {};

      Object.entries(data).forEach(([key, val]) => {
        if (val !== "" && val !== undefined) {
          cleanedData[key] = val;
        }
      });

      const formattedPayload = {
        ...cleanedData,
        subcategoryId: data.subcategoryId ? parseInt(data.subcategoryId, 10) : undefined,
        price: data.price ? parseFloat(data.price) : null,
      };

      console.log(`[EditAdPage] Sending PUT request to /ads/${id}:`, formattedPayload);
      await api.put(`/ads/${id}`, formattedPayload);

      toast.success("Listing updated successfully!");
      navigate(`/ads/${id}`);
    } catch (err: any) {
      console.error("[EditAdPage] Failed to update listing:", err);
      toast.error(err.response?.data?.message || "Failed to update listing.");
    } finally {
      setSubmitting(false);
    }
  };

  const onInvalid = (errors: any) => {
    console.warn("[EditAdPage] Form validation errors:", errors);
    toast.error("Please verify all fields are filled out correctly.");
  };

  if (adLoading) {
    return (
      <div className="max-w-4xl mx-auto py-8">
        <CardSkeleton />
      </div>
    );
  }

  if (adError || !adDetails) {
    return (
      <div className="text-center py-20 bg-ivory rounded-2xl border border-sand max-w-xl mx-auto my-8">
        <h2 className="text-2xl font-bold text-walnut">Listing Not Found</h2>
        <p className="text-bronze mt-2">The listing you are trying to edit does not exist or has been removed.</p>
        <Link to="/dashboard" className="mt-4 inline-block text-amber font-semibold hover:underline">
          Return to Dashboard
        </Link>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto py-8 space-y-6">
      {/* Back button link */}
      <Link
        to="/dashboard"
        className="inline-flex items-center gap-1.5 text-sm font-semibold text-bronze hover:text-walnut transition-colors"
      >
        <ArrowLeft size={16} /> Back to Dashboard
      </Link>

      <Card hoverable={false} className="p-8">
        <div className="space-y-8">
          {/* Header */}
          <div className="flex items-center justify-between border-b border-sand pb-4">
            <div>
              <h1 className="text-2xl font-black text-walnut leading-none">Edit Classified Listing</h1>
              <p className="text-xs text-bronze mt-1">Update fields, details, and filters for listing #{id}</p>
            </div>
            <Edit3 className="text-gold shrink-0" size={28} />
          </div>

          <form onSubmit={handleSubmit(onSubmit, onInvalid)} className="space-y-8">
            {/* Primary Details Section */}
            <div className="space-y-6">
              <h3 className="text-sm font-bold uppercase tracking-wider text-walnut border-b border-sand/40 pb-2">
                1. General Information
              </h3>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <Select
                  label="Category Type"
                  options={
                    categories?.map((cat) => {
                      const value = cat.name.toUpperCase().replace(" ", "_");
                      const label = cat.name
                        .replace("_", " ")
                        .toLowerCase()
                        .replace(/\b\w/g, (l) => l.toUpperCase());
                      return { value, label };
                    }) || []
                  }
                  placeholder="Select Category"
                  error={errors.adType?.message}
                  {...register("adType")}
                  onChange={(e) => {
                    setValue("adType", e.target.value as AdType);
                    setValue("subcategoryId", "");
                  }}
                />

                {selectedCategory && subcategories && (
                  <Select
                    label="Subcategory"
                    options={subcategories.map((sub) => ({
                      value: sub.id.toString(),
                      label: sub.name,
                    }))}
                    placeholder="Select Subcategory"
                    error={errors.subcategoryId?.message}
                    {...register("subcategoryId")}
                  />
                )}
              </div>

              <Input
                label="Title"
                type="text"
                placeholder="Enter a descriptive title"
                error={errors.title?.message}
                {...register("title")}
              />

              <Textarea
                label="Description"
                placeholder="Detailed description of your listing..."
                error={errors.description?.message}
                {...register("description")}
              />

              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <Input
                  label="Price (₹)"
                  type="text"
                  placeholder="0.00"
                  disabled={watch("isFree")}
                  error={errors.price?.message}
                  {...register("price")}
                />
                <Input
                  label="Location"
                  type="text"
                  placeholder="e.g. New York, NY"
                  error={errors.location?.message}
                  {...register("location")}
                />
              </div>

              <div className="flex items-center gap-6 pt-2">
                <label className="flex items-center gap-2 text-sm font-medium text-coffee cursor-pointer">
                  <input
                    type="checkbox"
                    className="rounded text-gold focus:ring-gold"
                    {...register("isFree")}
                    onChange={(e) => {
                      setValue("isFree", e.target.checked);
                      if (e.target.checked) setValue("price", "0");
                    }}
                  />
                  Listed as Free
                </label>

                <label className="flex items-center gap-2 text-sm font-medium text-coffee cursor-pointer">
                  <input
                    type="checkbox"
                    className="rounded text-gold focus:ring-gold"
                    {...register("deliveryAvailable")}
                  />
                  Delivery Available
                </label>
              </div>
            </div>

            {/* Category Specific Options Section */}
            <div className="space-y-6">
              <h3 className="text-sm font-bold uppercase tracking-wider text-walnut border-b border-sand/40 pb-2">
                2. Category Specific Parameters
              </h3>

              <div className="flex items-start gap-2 bg-linen p-4 rounded-xl text-xs text-bronze border border-sand">
                <Info size={16} className="text-gold shrink-0 mt-0.5" />
                <p>
                  Update specific attribute filters for this category below.
                </p>
              </div>

              <AdFilterPanel category={selectedCategory} register={register} />
            </div>

            {/* Submit / Cancel Actions */}
            <div className="flex items-center justify-end gap-4 pt-6 border-t border-sand">
              <Link to="/dashboard">
                <Button type="button" variant="ghost">
                  Cancel
                </Button>
              </Link>
              <Button
                type="submit"
                variant="primary"
                isLoading={submitting}
                leftIcon={<Save size={18} />}
              >
                Save Changes
              </Button>
            </div>
          </form>
        </div>
      </Card>
    </div>
  );
}
