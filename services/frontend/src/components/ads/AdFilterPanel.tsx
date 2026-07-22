import React from "react";
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
              { value: "HOUSE", label: "House" },
              { value: "TOWNHOUSE", label: "Townhouse" },
              { value: "DUPLEX", label: "Duplex" },
              { value: "ROOM", label: "Room" },
            ]}
            placeholder="Select Type"
            {...register("housingType")}
          />
          <Select
            label="Rent Period"
            options={[
              { value: "MONTHLY", label: "Monthly" },
              { value: "WEEKLY", label: "Weekly" },
              { value: "DAILY", label: "Daily" },
            ]}
            placeholder="Select Period"
            {...register("rentPeriod")}
          />
          <Select
            label="Laundry"
            options={[
              { value: "in_unit", label: "In-unit" },
              { value: "hookups", label: "Hookups" },
              { value: "in_building", label: "In-building" },
              { value: "on_site", label: "On-site" },
              { value: "no_laundry", label: "No Laundry" },
            ]}
            placeholder="Select Laundry"
            {...register("laundry")}
          />
          <Select
            label="Parking"
            options={[
              { value: "attached_garage", label: "Attached Garage" },
              { value: "detached_garage", label: "Detached Garage" },
              { value: "carport", label: "Carport" },
              { value: "off_street", label: "Off-street" },
              { value: "street", label: "Street" },
              { value: "valet", label: "Valet" },
              { value: "no_parking", label: "No Parking" },
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
              { value: "FULL_TIME", label: "Full-time" },
              { value: "PART_TIME", label: "Part-time" },
              { value: "CONTRACT", label: "Contract" },
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
              { value: "BEAUTY", label: "Beauty & Health" },
              { value: "CELL_PHONE", label: "Cell Phone & Mobile" },
              { value: "COMPUTER", label: "Computer & Tech" },
              { value: "HOUSEHOLD", label: "Household Services" },
              { value: "LABOR", label: "Labor & Moving" },
              { value: "LESSONS", label: "Lessons & Tutoring" },
              { value: "PET", label: "Pet Services" },
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
              { value: "COMPUTER", label: "Computer / Tech" },
              { value: "CREATIVE", label: "Creative (Design / Photo)" },
              { value: "CREW", label: "Crew (Event help)" },
              { value: "DOMESTIC", label: "Domestic (Cleaning / Yard)" },
              { value: "EVENT", label: "Event" },
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
              { value: "ACTIVITIES", label: "Activities" },
              { value: "ARTISTS", label: "Artists" },
              { value: "GENERAL", label: "General" },
              { value: "LOST_FOUND", label: "Lost & Found" },
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
              { value: "HIGH_SCHOOL", label: "High School" },
              { value: "ASSOCIATE", label: "Associate Degree" },
              { value: "BACHELOR", label: "Bachelor's Degree" },
              { value: "MASTER", label: "Master's Degree" },
              { value: "DOCTORATE", label: "Doctorate Degree" },
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
