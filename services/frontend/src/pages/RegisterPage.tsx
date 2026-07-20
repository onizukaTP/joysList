import React, { useState } from "react";
import { Link, useNavigate } from "react-router";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { UserPlus, LogIn } from "lucide-react";
import { useAuthStore } from "../store/authStore";
import Input from "../components/ui/Input";
import Button from "../components/ui/Button";
import Card from "../components/ui/Card";
import { toast } from "sonner";

const registerSchema = z
  .object({
    username: z
      .string()
      .min(3, "Username must be at least 3 characters")
      .max(20, "Username cannot exceed 20 characters")
      .regex(/^[a-zA-Z0-9_]+$/, "Username can only contain letters, numbers, and underscores"),
    email: z.string().min(1, "Email is required").email("Invalid email address"),
    password: z.string().min(6, "Password must be at least 6 characters"),
    confirmPassword: z.string().min(1, "Please confirm your password"),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"],
  });

type RegisterFormValues = z.infer<typeof registerSchema>;

export default function RegisterPage() {
  const { register: signup, isLoading } = useAuthStore();
  const navigate = useNavigate();
  const [serverError, setServerError] = useState<string | null>(null);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<RegisterFormValues>({
    resolver: zodResolver(registerSchema),
  });

  const onSubmit = async (data: RegisterFormValues) => {
    setServerError(null);
    try {
      const { confirmPassword, ...submitData } = data;
      await signup(submitData);
      toast.success("Welcome! Your JoysList account has been created.");
      navigate("/");
    } catch (error: any) {
      const errMsg = error.response?.data?.message || "Registration failed. Try another username/email.";
      setServerError(errMsg);
      toast.error(errMsg);
    }
  };

  return (
    <div className="flex-1 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <Card hoverable={false} className="w-full max-w-md">
        <div className="space-y-6">
          <div className="text-center">
            <h2 className="text-3xl font-extrabold text-walnut">Join JoysList</h2>
            <p className="mt-2 text-sm text-bronze">
              Create an account to post listings and connect locally
            </p>
          </div>

          {serverError && (
            <div className="bg-bronze-dark/10 border border-bronze-dark/30 rounded-xl p-3 text-sm text-bronze-dark">
              {serverError}
            </div>
          )}

          <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            <Input
              label="Username"
              type="text"
              placeholder="choose_username"
              error={errors.username?.message}
              {...register("username")}
            />

            <Input
              label="Email Address"
              type="email"
              placeholder="you@example.com"
              error={errors.email?.message}
              {...register("email")}
            />

            <Input
              label="Password"
              type="password"
              placeholder="••••••••"
              error={errors.password?.message}
              {...register("password")}
            />

            <Input
              label="Confirm Password"
              type="password"
              placeholder="••••••••"
              error={errors.confirmPassword?.message}
              {...register("confirmPassword")}
            />

            <Button
              type="submit"
              variant="primary"
              className="w-full mt-6"
              isLoading={isLoading}
              rightIcon={<UserPlus size={18} />}
            >
              Sign Up
            </Button>
          </form>

          <div className="relative flex py-2 items-center">
            <div className="flex-grow border-t border-sand"></div>
            <span className="flex-shrink mx-4 text-xs text-sand-dark uppercase tracking-wider">
              Already member?
            </span>
            <div className="flex-grow border-t border-sand"></div>
          </div>

          <div className="text-center">
            <Link to="/login">
              <Button
                type="button"
                variant="secondary"
                className="w-full"
                leftIcon={<LogIn size={18} />}
              >
                Sign In
              </Button>
            </Link>
          </div>
        </div>
      </Card>
    </div>
  );
}
