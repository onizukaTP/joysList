import React, { type ReactNode } from "react";
import { motion, type HTMLMotionProps } from "framer-motion";

interface CardProps extends HTMLMotionProps<"div"> {
  children: ReactNode;
  hoverable?: boolean;
  padding?: "sm" | "md" | "lg";
}

const paddingStyles = {
  sm: "p-4",
  md: "p-6",
  lg: "p-8",
};

export default function Card({
  children,
  hoverable = true,
  padding = "md",
  className = "",
  ...props
}: CardProps) {
  return (
    <motion.div
      whileHover={hoverable ? { y: -4, scale: 1.005 } : undefined}
      transition={{ type: "spring", stiffness: 300, damping: 20 }}
      className={`
        bg-ivory border border-sand rounded-2xl shadow-sm
        ${hoverable ? "hover:shadow-lg hover:border-caramel/40 cursor-pointer" : ""}
        transition-shadow duration-300
        ${paddingStyles[padding]}
        ${className}
      `}
      {...props}
    >
      {children}
    </motion.div>
  );
}
