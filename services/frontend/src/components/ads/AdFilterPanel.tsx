import type { UseFormRegister } from "react-hook-form";
import type { AdType } from "../../types";
import Input from "../ui/Input";
import Select from "../ui/Select";

interface AdFilterPanelProps {
  category: AdType | "";
  register: UseFormRegister<any>;
}

export default function AdFilterPanel({ category, register }: AdFilterPanelProps) {
  if (!category) return null;

  return (
    <div className="bg-linen/50 border border-sand/50 rounded-2xl p-6 space-y-5 animate-fadeIn">
      <h3 className="text-sm font-bold uppercase tracking-wider text-walnut border-b border-sand/40 pb-2">
        Category Details ({category.replace("_", " ")})
      </h3>

      {category === "HOUSING" && (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          <Input
            label="Beds"
            type="number"
            min={0}
            placeholder="0"
            {...register("numberOfBeds", { valueAsNumber: true })}
          />
          <Input
            label="Baths"
            type="number"
            min={0}
            step={0.5}
            placeholder="0"
            {...register("numberOfBathrooms", { valueAsNumber: true })}
          />
          <Select
            label="Housing Type"
            options={[
              { value: "APARTMENT", label: "Apartment" },
              { value: "CONDO", label: "Condo" },
              { value: "TOWNHOUSE", label: "Townhouse" },
              { value: "HOUSE", label: "House" },
              { value: "LOFT", label: "Loft" },
              { value: "CABIN", label: "Cabin" },
              { value: "STUDIO", label: "Studio" },
              { value: "DUPLEX", label: "Duplex" },
              { value: "OFFICE", label: "Office" },
              { value: "OTHER", label: "Other" },
            ]}
            placeholder="Select Type"
            {...register("housingType")}
          />
          <Select
            label="Rent Period"
            options={[
              { value: "DAILY", label: "Daily" },
              { value: "WEEKLY", label: "Weekly" },
              { value: "MONTHLY", label: "Monthly" },
            ]}
            placeholder="Select Period"
            {...register("rentPeriod")}
          />
          <Select
            label="Laundry"
            options={[
              { value: "UNIT", label: "In-unit" },
              { value: "HOOKUPS", label: "Hookups" },
              { value: "INBUILDING", label: "In Building" },
              { value: "ONSITE", label: "On-site" },
              { value: "NOLAUNDRY", label: "No Laundry" },
            ]}
            placeholder="Select Laundry"
            {...register("laundry")}
          />
          <Select
            label="Parking"
            options={[
              { value: "CARPORT", label: "Carport" },
              { value: "ATTACHED_GARAGE", label: "Attached Garage" },
              { value: "DETACHED_GARAGE", label: "Detached Garage" },
              { value: "OFF_STREET", label: "Off Street" },
              { value: "STREET", label: "Street Parking" },
              { value: "VALET", label: "Valet" },
              { value: "NOPARKING", label: "No Parking" },
            ]}
            placeholder="Select Parking"
            {...register("parking")}
          />
          <div className="col-span-full grid grid-cols-2 md:grid-cols-4 gap-4 pt-2">
            <label className="flex items-center gap-2 text-sm font-medium text-coffee cursor-pointer">
              <input type="checkbox" {...register("catsOk")} className="rounded text-gold focus:ring-gold" />
              Cats OK
            </label>
            <label className="flex items-center gap-2 text-sm font-medium text-coffee cursor-pointer">
              <input type="checkbox" {...register("dogsOk")} className="rounded text-gold focus:ring-gold" />
              Dogs OK
            </label>
            <label className="flex items-center gap-2 text-sm font-medium text-coffee cursor-pointer">
              <input type="checkbox" {...register("furnished")} className="rounded text-gold focus:ring-gold" />
              Furnished
            </label>
          </div>
        </div>
      )}

      {category === "FOR_SALE" && (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <Select
            label="Sold By"
            options={[
              { value: "OWNER", label: "Owner" },
              { value: "DEALER", label: "Dealer" },
            ]}
            placeholder="Select seller"
            {...register("soldBy")}
          />
          <Select
            label="Condition"
            options={[
              { value: "NEW", label: "New" },
              { value: "LIKE_NEW", label: "Like New" },
              { value: "EXCELLENT", label: "Excellent" },
              { value: "GOOD", label: "Good" },
              { value: "FAIR", label: "Fair" },
              { value: "SALVAGE", label: "Salvage" },
              { value: "USED", label: "Used" },
            ]}
            placeholder="Select condition"
            {...register("condition")}
          />
        </div>
      )}

      {category === "JOBS" && (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <Select
            label="Employment Type"
            options={[
              { value: "FULL_TIME", label: "Full Time" },
              { value: "PART_TIME", label: "Part Time" },
              { value: "CONTRACT", label: "Contract" },
              { value: "EMPLOYEE_CHOICE", label: "Employee Choice" },
            ]}
            placeholder="Select employment"
            {...register("employmentType")}
          />
          <div className="flex flex-col justify-center gap-2 pt-5">
            <label className="flex items-center gap-2 text-sm font-medium text-coffee cursor-pointer">
              <input type="checkbox" {...register("nonProfitOrganization")} className="rounded text-gold" />
              Non-Profit
            </label>
            <label className="flex items-center gap-2 text-sm font-medium text-coffee cursor-pointer">
              <input type="checkbox" {...register("internship")} className="rounded text-gold" />
              Internship
            </label>
            <label className="flex items-center gap-2 text-sm font-medium text-coffee cursor-pointer">
              <input type="checkbox" {...register("telecommutingOk")} className="rounded text-gold" />
              Remote OK
            </label>
          </div>
        </div>
      )}

      {category === "SERVICES" && (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <Select
            label="Service Type"
            options={[
              { value: "AUTOMOTIVE", label: "Automotive" },
              { value: "BEAUTY", label: "Beauty" },
              { value: "CELL_PHONE_OR_MOBILE", label: "Cell Phone / Mobile" },
              { value: "COMPUTER", label: "Computer" },
              { value: "CYCLE", label: "Cycle" },
              { value: "EVENT", label: "Event" },
              { value: "FARM_AND_GARDEN", label: "Farm & Garden" },
              { value: "FINANCIAL", label: "Financial" },
              { value: "HEALTH_AND_WELLNESS", label: "Health & Wellness" },
              { value: "HOUSEHOLD", label: "Household" },
              { value: "LABOR_AND_MOVING", label: "Labor & Moving" },
              { value: "LEGAL", label: "Legal" },
              { value: "LESSONS_AND_TUTORING", label: "Lessons & Tutoring" },
              { value: "MARINE", label: "Marine" },
              { value: "PET", label: "Pet" },
              { value: "REAL_ESTATE", label: "Real Estate" },
              { value: "SKILLED_TRADE", label: "Skilled Trade" },
              { value: "SMALL_BIZ_ADS", label: "Small Biz Ads" },
              { value: "TRAVEL_OR_VACATION", label: "Travel / Vacation" },
              { value: "WRITE_OR_EDIT_OR_TRANSLATION", label: "Writing / Editing / Translation" },
            ]}
            placeholder="Select Service Type"
            {...register("serviceType")}
          />
        </div>
      )}

      {category === "GIGS" && (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <Select
            label="Gig Category"
            options={[
              { value: "LABOR", label: "Labor" },
              { value: "DOMESTIC", label: "Domestic" },
              { value: "EVENT", label: "Event" },
              { value: "COMPUTER", label: "Computer" },
              { value: "CREATIVE", label: "Creative" },
              { value: "CREW", label: "Crew" },
              { value: "TALENT", label: "Talent" },
              { value: "WRITING", label: "Writing" },
            ]}
            placeholder="Select Gig Type"
            {...register("gigs")}
          />
          <Select
            label="Payment Status"
            options={[
              { value: "PAID", label: "Paid" },
              { value: "UNPAID", label: "Unpaid" },
            ]}
            placeholder="Select Payment"
            {...register("status")}
          />
        </div>
      )}

      {category === "COMMUNITY" && (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <Select
            label="Community Group"
            options={[
              { value: "ACTIVITY_PARTNERS", label: "Activity Partners" },
              { value: "ARTISTS", label: "Artists" },
              { value: "CHILDCARE", label: "Childcare" },
              { value: "GENERAL", label: "General" },
              { value: "GROUPS", label: "Groups" },
              { value: "LOCAL_NEWS_AND_VIEWS", label: "Local News & Views" },
              { value: "LOST_AND_FOUND", label: "Lost & Found" },
              { value: "MISSED_CONNECTIONS", label: "Missed Connections" },
              { value: "MUSICIANS", label: "Musicians" },
              { value: "PETS", label: "Pets" },
              { value: "POLITICS", label: "Politics" },
              { value: "RANTS_AND_RAVES", label: "Rants & Raves" },
              { value: "RIDESHARE", label: "Rideshare" },
              { value: "VOLUNTEERS", label: "Volunteers" },
            ]}
            placeholder="Select Group"
            {...register("communityType")}
          />
          <Select
            label="Lost / Found"
            options={[
              { value: "LOST", label: "Lost" },
              { value: "FOUND", label: "Found" },
            ]}
            placeholder="Select Action"
            {...register("lostOrFound")}
          />
        </div>
      )}

      {category === "RESUMES" && (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <Select
            label="Completed Education"
            options={[
              { value: "LESS_THAN_HIGH_SCHOOL", label: "Less than High School" },
              { value: "HIGH_SCHOOL_OR_GED", label: "High School or GED" },
              { value: "SOME_COLLEGE", label: "Some College" },
              { value: "ASSOCIATES", label: "Associates Degree" },
              { value: "BACHELORS", label: "Bachelors Degree" },
              { value: "MASTERS", label: "Masters Degree" },
              { value: "DOCTORAL", label: "Doctoral Degree" },
            ]}
            placeholder="Select Education"
            {...register("educationCompleted")}
          />
          <div className="col-span-full grid grid-cols-2 md:grid-cols-3 gap-2 pt-2">
            <label className="flex items-center gap-2 text-sm font-medium text-coffee cursor-pointer">
              <input type="checkbox" {...register("availableMornings")} className="rounded text-gold" />
              Mornings
            </label>
            <label className="flex items-center gap-2 text-sm font-medium text-coffee cursor-pointer">
              <input type="checkbox" {...register("availableAfternoons")} className="rounded text-gold" />
              Afternoons
            </label>
            <label className="flex items-center gap-2 text-sm font-medium text-coffee cursor-pointer">
              <input type="checkbox" {...register("availableEvenings")} className="rounded text-gold" />
              Evenings
            </label>
            <label className="flex items-center gap-2 text-sm font-medium text-coffee cursor-pointer">
              <input type="checkbox" {...register("availableOvernights")} className="rounded text-gold" />
              Overnights
            </label>
            <label className="flex items-center gap-2 text-sm font-medium text-coffee cursor-pointer">
              <input type="checkbox" {...register("availableWeekdays")} className="rounded text-gold" />
              Weekdays
            </label>
            <label className="flex items-center gap-2 text-sm font-medium text-coffee cursor-pointer">
              <input type="checkbox" {...register("availableWeekends")} className="rounded text-gold" />
              Weekends
            </label>
          </div>
        </div>
      )}
    </div>
  );
}
