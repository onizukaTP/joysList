import React, { forwardRef, type SelectHTMLAttributes } from "react";
import { ChevronDown } from "lucide-react";

interface SelectOption {
  value: string;
  label: string;
}

interface SelectProps extends Omit<SelectHTMLAttributes<HTMLSelectElement>, "children"> {
  label?: string;
  error?: string;
  options: SelectOption[];
  placeholder?: string;
}

const Select = forwardRef<HTMLSelectElement, SelectProps>(
  ({ label, error, options, placeholder, className = "", ...props }, ref) => {
    return (
      <div className="flex flex-col gap-1.5 w-full">
        {label && (
          <label className="text-sm font-medium text-walnut">{label}</label>
        )}
        <div className="relative">
          <select
            ref={ref}
            className={`
              w-full bg-ivory border border-sand rounded-xl
              px-4 py-2.5 pr-10 text-coffee appearance-none font-medium text-sm
              focus:outline-none focus:ring-2 focus:ring-gold/40 focus:border-gold
              transition-all duration-200 cursor-pointer
              ${error ? "border-bronze-dark ring-1 ring-bronze-dark/30" : ""}
              ${className}
            `}
            {...props}
          >
            {placeholder && (
              <option value="" className="bg-ivory text-sand-dark py-2">
                {placeholder}
              </option>
            )}
            {options.map((opt) => (
              <option key={opt.value} value={opt.value} className="bg-ivory text-coffee py-2 font-normal">
                {opt.label}
              </option>
            ))}
          </select>
          <ChevronDown
            className="absolute right-3 top-1/2 -translate-y-1/2 text-sand-dark pointer-events-none"
            size={18}
          />
        </div>
        {error && <p className="text-sm text-bronze-dark">{error}</p>}
      </div>
    );
  }
);

Select.displayName = "Select";
export default Select;
