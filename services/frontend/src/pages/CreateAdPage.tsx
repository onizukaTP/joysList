import React, { useState } from "react";
import { useNavigate } from "react-router";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { useQuery } from "@tanstack/react-query";
import api from "../services/api";
import type { Category, Subcategory, AdType } from "../types";
import Input from "../components/ui/Input";
import Textarea from "../components/ui/Textarea";
import Select from "../components/ui/Select";
import Button from "../components/ui/Button";
import Card from "../components/ui/Card";
import AdFilterPanel from "../components/ads/AdFilterPanel";
import { toast } from "sonner";
import { PlusCircle, Info, ChevronRight, ChevronLeft, ImagePlus, Upload } from "lucide-react";

const adSchema = z.object({
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
});

type AdFormValues = z.infer<typeof adSchema>;

export default function CreateAdPage() {
  const navigate = useNavigate();
  const [step, setStep] = useState(1);
  const [loading, setLoading] = useState(false);

  const {
    register,
    handleSubmit,
    watch,
    setValue,
    formState: { errors },
  } = useForm<AdFormValues>({
    resolver: zodResolver(adSchema),
    defaultValues: {
      isFree: false,
      deliveryAvailable: false,
      catsOk: false,
      dogsOk: false,
      furnished: false,
    },
  });

  const selectedCategory = watch("adType");

  // 1. Fetch categories
  const { data: categories } = useQuery<Category[]>({
    queryKey: ["categories"],
    queryFn: async () => {
      const res = await api.get("/categories");
      return res.data;
    },
  });

  const activeCategoryObject = categories?.find(
    (c) => c.name.toUpperCase().replace(" ", "_") === selectedCategory
  );

  // 2. Fetch subcategories
  const { data: subcategories } = useQuery<Subcategory[]>({
    queryKey: ["subcategories", activeCategoryObject?.id],
    enabled: !!activeCategoryObject,
    queryFn: async () => {
      const res = await api.get(`/subcategories/category/${activeCategoryObject?.id}`);
      return res.data;
    },
  });

  const onSubmit = async (data: AdFormValues) => {
    console.log("[CreateAd] Submitting form data:", data);
    setLoading(true);
    try {
      // Clean up empty optional fields and convert frontend casing to backend Enums
      const cleanedData: Record<string, any> = {};
      
      const LAUNDRY_MAP: Record<string, string> = {
        in_unit: "UNIT",
        hookups: "HOOKUPS",
        in_building: "INBUILDING",
        on_site: "ONSITE",
        no_laundry: "NOLAUNDRY",
      };

      const PARKING_MAP: Record<string, string> = {
        attached_garage: "ATTACHED_GARAGE",
        detached_garage: "DETACHED_GARAGE",
        carport: "CARPORT",
        off_street: "OFF_STREET",
        street: "STREET",
        valet: "VALET",
        no_parking: "NOPARKING",
      };

      Object.entries(data).forEach(([key, val]) => {
        if (val !== "" && val !== undefined) {
          if (key === "laundry" && typeof val === "string" && LAUNDRY_MAP[val]) {
            cleanedData[key] = LAUNDRY_MAP[val];
          } else if (key === "parking" && typeof val === "string" && PARKING_MAP[val]) {
            cleanedData[key] = PARKING_MAP[val];
          } else {
            cleanedData[key] = val;
          }
        }
      });

      const formattedPayload = {
        ...cleanedData,
        subcategoryId: parseInt(data.subcategoryId, 10),
        price: data.price ? parseFloat(data.price) : null,
      };

      // Post to unified AdFacadeService endpoint (POST /api/v1/ads)
      console.log(`[CreateAd] Posting payload to /ads`, formattedPayload);
      await api.post("/ads", formattedPayload);

      toast.success("Ad created successfully!");
      navigate("/browse");
    } catch (e: any) {
      console.error("[CreateAd] Failed to submit listing:", e);
      toast.error(e.response?.data?.message || "Failed to create ad listing");
    } finally {
      setLoading(false);
    }
  };

  const onInvalid = (errors: any) => {
    console.warn("[CreateAd] Form validation errors blocked submit:", errors);
    toast.error("Please fill in all required fields properly.");
  };

  const nextStep = () => setStep((s) => s + 1);
  const prevStep = () => setStep((s) => s - 1);

  return (
    <div className="max-w-3xl mx-auto py-8">
      <Card hoverable={false} className="p-8">
        <div className="space-y-8">
          {/* Header */}
          <div className="flex items-center justify-between border-b border-sand pb-4">
            <div>
              <h1 className="text-2xl font-black text-walnut leading-none">Post a Classified Listing</h1>
              <p className="text-xs text-bronze mt-1">Step {step} of 3</p>
            </div>
            <PlusCircle className="text-gold shrink-0" size={28} />
          </div>

          <form onSubmit={handleSubmit(onSubmit, onInvalid)} className="space-y-6">
            {/* Step 1: Category Selection */}
            {step === 1 && (
              <div className="space-y-6">
                <Select
                  label="Category Type"
                  options={
                    categories?.map((cat) => {
                      const value = cat.name.toUpperCase().replace(" ", "_");
                      // Format Title Case label (e.g. For Sale)
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

                <div className="flex justify-end pt-4">
                  <Button
                    type="button"
                    variant="primary"
                    disabled={!selectedCategory || !watch("subcategoryId")}
                    onClick={nextStep}
                    rightIcon={<ChevronRight size={18} />}
                  >
                    Continue
                  </Button>
                </div>
              </div>
            )}

            {/* Step 2: Main Ad Fields */}
            {step === 2 && (
              <div className="space-y-6">
                <Input
                  label="Title"
                  type="text"
                  placeholder="Enter a descriptive title"
                  error={errors.title?.message}
                  {...register("title")}
                />

                <Textarea
                  label="Description"
                  placeholder="Detailed description of what you are posting..."
                  error={errors.description?.message}
                  {...register("description")}
                />

                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                  <Input
                    label="Price ($)"
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

                {/* Picture Upload Section */}
                <div className="space-y-2 pt-2">
                  <label className="text-sm font-medium text-walnut">Listing Pictures</label>
                  <div className="border-2 border-dashed border-sand hover:border-gold/60 bg-linen/30 rounded-2xl p-6 text-center space-y-3 transition-colors">
                    <div className="flex flex-col items-center gap-2 text-sand-dark">
                      <ImagePlus size={36} className="text-gold stroke-[1.5]" />
                      <p className="text-xs font-semibold text-coffee">
                        Drag and drop photos here, or <span className="text-amber underline cursor-pointer">browse</span>
                      </p>
                      <p className="text-[11px] text-sand-dark">
                        Supports PNG, JPG, or WEBP up to 5MB (Simulated Gallery UI)
                      </p>
                    </div>
                    <input
                      type="file"
                      accept="image/*"
                      multiple
                      className="hidden"
                      id="ad-image-upload"
                      onChange={() => {
                        setValue("hasImage", true);
                        toast.success("Photos selected for upload!");
                      }}
                    />
                    <label htmlFor="ad-image-upload" className="inline-block">
                      <Button type="button" variant="secondary" size="sm" leftIcon={<Upload size={14} />}>
                        Select Images
                      </Button>
                    </label>
                  </div>
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

                <div className="flex justify-between pt-6 border-t border-sand/40">
                  <Button
                    type="button"
                    variant="ghost"
                    onClick={prevStep}
                    leftIcon={<ChevronLeft size={18} />}
                  >
                    Back
                  </Button>
                  <Button
                    type="button"
                    variant="primary"
                    onClick={nextStep}
                    disabled={!watch("title") || !watch("description") || !watch("location")}
                    rightIcon={<ChevronRight size={18} />}
                  >
                    Filters & Details
                  </Button>
                </div>
              </div>
            )}

            {/* Step 3: Category specific details */}
            {step === 3 && (
              <div className="space-y-6">
                <div className="flex items-start gap-2 bg-linen p-4 rounded-xl text-xs text-bronze border border-sand">
                  <Info size={16} className="text-gold shrink-0 mt-0.5" />
                  <p>
                    Provide filters below to make your listing discoverable in category searches.
                  </p>
                </div>

                {/* Sub-panel filtering */}
                <AdFilterPanel category={selectedCategory} register={register} />

                <div className="flex justify-between pt-6 border-t border-sand/40">
                  <Button
                    type="button"
                    variant="ghost"
                    onClick={prevStep}
                    leftIcon={<ChevronLeft size={18} />}
                  >
                    Back
                  </Button>
                  <Button
                    type="submit"
                    variant="primary"
                    isLoading={loading}
                    rightIcon={<PlusCircle size={18} />}
                  >
                    Publish Listing
                  </Button>
                </div>
              </div>
            )}
          </form>
        </div>
      </Card>
    </div>
  );
}
