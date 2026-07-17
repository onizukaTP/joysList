import React, { forwardRef, type TextareaHTMLAttributes } from "react";

interface TextareaProps extends TextareaHTMLAttributes<HTMLTextAreaElement> {
  label?: string;
  error?: string;
}

const Textarea = forwardRef<HTMLTextAreaElement, TextareaProps>(
  ({ label, error, className = "", ...props }, ref) => {
    return (
      <div className="flex flex-col gap-1.5 w-full">
        {label && (
          <label className="text-sm font-medium text-walnut">{label}</label>
        )}
        <textarea
          ref={ref}
          className={`
            w-full bg-ivory border border-sand rounded-xl
            px-4 py-3 text-coffee min-h-[120px] resize-y
            placeholder:text-sand-dark
            focus:outline-none focus:ring-2 focus:ring-gold/40 focus:border-gold
            transition-all duration-200
            ${error ? "border-bronze-dark ring-1 ring-bronze-dark/30" : ""}
            ${className}
          `}
          {...props}
        />
        {error && <p className="text-sm text-bronze-dark">{error}</p>}
      </div>
    );
  }
);

Textarea.displayName = "Textarea";
export default Textarea;
