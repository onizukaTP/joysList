import React, { useState } from "react";
import { Link, useNavigate, useLocation } from "react-router";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { LogIn, UserPlus } from "lucide-react";
import { useAuthStore } from "../store/authStore";
import Input from "../components/ui/Input";
import Button from "../components/ui/Button";
import Card from "../components/ui/Card";
import { toast } from "sonner";

const loginSchema = z.object({
  username: z.string().min(1, "Username is required"),
  password: z.string().min(6, "Password must be at least 6 characters"),
});

type LoginFormValues = z.infer<typeof loginSchema>;

export default function LoginPage() {
  const { login, isLoading } = useAuthStore();
  const navigate = useNavigate();
  const location = useLocation();
  const [serverError, setServerError] = useState<string | null>(null);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
  });

  const from = (location.state as any)?.from?.pathname || "/";

  const onSubmit = async (data: LoginFormValues) => {
    setServerError(null);
    try {
      await login(data);
      toast.success("Welcome back to JoysList!");
      navigate(from, { replace: true });
    } catch (error: any) {
      const errMsg = error.response?.data?.message || "Invalid username or password";
      setServerError(errMsg);
      toast.error(errMsg);
    }
  };

  return (
    <div className="flex-1 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <Card hoverable={false} className="w-full max-w-md">
        <div className="space-y-6">
          <div className="text-center">
            <h2 className="text-3xl font-extrabold text-walnut">Sign In</h2>
            <p className="mt-2 text-sm text-bronze">
              Access your personal dashboard and listings
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
              placeholder="Enter your username"
              error={errors.username?.message}
              {...register("username")}
            />

            <Input
              label="Password"
              type="password"
              placeholder="••••••••"
              error={errors.password?.message}
              {...register("password")}
            />

            <Button
              type="submit"
              variant="primary"
              className="w-full mt-6"
              isLoading={isLoading}
              rightIcon={<LogIn size={18} />}
            >
              Sign In
            </Button>
          </form>

          <div className="relative flex py-2 items-center">
            <div className="flex-grow border-t border-sand"></div>
            <span className="flex-shrink mx-4 text-xs text-sand-dark uppercase tracking-wider">
              New to JoysList?
            </span>
            <div className="flex-grow border-t border-sand"></div>
          </div>

          <div className="text-center">
            <Link to="/register">
              <Button
                type="button"
                variant="secondary"
                className="w-full"
                leftIcon={<UserPlus size={18} />}
              >
                Create an account
              </Button>
            </Link>
          </div>
        </div>
      </Card>
    </div>
  );
}
