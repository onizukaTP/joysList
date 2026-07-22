import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import api from "../services/api";
import type { UserProfile, UpdateProfileRequest } from "../types";
import Card from "../components/ui/Card";
import Input from "../components/ui/Input";
import Textarea from "../components/ui/Textarea";
import Button from "../components/ui/Button";
import Avatar from "../components/ui/Avatar";
import { toast } from "sonner";
import { User, Save, Upload, Trash2, ArrowLeft } from "lucide-react";

const profileSchema = z.object({
  bio: z.string().max(500, "Bio cannot exceed 500 characters").optional(),
  phone: z.string().optional(),
  location: z.string().optional(),
});

type ProfileFormValues = z.infer<typeof profileSchema>;

export default function EditProfilePage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const [uploadingAvatar, setUploadingAvatar] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<ProfileFormValues>({
    resolver: zodResolver(profileSchema),
  });

  // 1. Fetch current profile details
  const { data: profile, isLoading } = useQuery<UserProfile>({
    queryKey: ["userProfile", id],
    queryFn: async () => {
      console.log(`[EditProfile] Fetching profile for user ID: ${id}`);
      const res = await api.get(`/users/${id}/profile`);
      return res.data;
    },
  });

  useEffect(() => {
    if (profile) {
      reset({
        bio: profile.bio || "",
        phone: profile.phone || "",
        location: profile.location || "",
      });
    }
  }, [profile, reset]);

  // 2. Profile update mutation
  const updateMutation = useMutation({
    mutationFn: async (data: UpdateProfileRequest) => {
      console.log(`[EditProfile] Updating profile for user ID: ${id}`, data);
      const res = await api.put(`/users/${id}/profile`, data);
      return res.data;
    },
    onSuccess: () => {
      toast.success("Profile updated successfully!");
      queryClient.invalidateQueries({ queryKey: ["userProfile", id] });
      navigate(`/profile/${id}`);
    },
    onError: (err: any) => {
      console.error("[EditProfile] Update failed:", err);
      toast.error(err.response?.data?.message || "Failed to update profile.");
    },
  });

  // 3. Avatar upload handler
  const handleAvatarUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);

    setUploadingAvatar(true);
    try {
      console.log(`[EditProfile] Uploading avatar for user ID: ${id}`);
      await api.post(`/users/${id}/avatar`, formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });
      toast.success("Avatar updated!");
      queryClient.invalidateQueries({ queryKey: ["userProfile", id] });
    } catch (err: any) {
      console.error("[EditProfile] Avatar upload failed:", err);
      toast.error(err.response?.data?.message || "Failed to upload avatar.");
    } finally {
      setUploadingAvatar(false);
    }
  };

  // 4. Avatar remove handler
  const handleAvatarRemove = async () => {
    if (!window.confirm("Remove avatar picture?")) return;
    try {
      console.log(`[EditProfile] Removing avatar for user ID: ${id}`);
      await api.delete(`/users/${id}/avatar`);
      toast.success("Avatar removed.");
      queryClient.invalidateQueries({ queryKey: ["userProfile", id] });
    } catch (err: any) {
      console.error("[EditProfile] Avatar removal failed:", err);
      toast.error(err.response?.data?.message || "Failed to remove avatar.");
    }
  };

  const onSubmit = (data: ProfileFormValues) => {
    updateMutation.mutate(data);
  };

  if (isLoading) {
    return (
      <div className="max-w-2xl mx-auto py-12 text-center text-bronze">
        Loading profile configuration...
      </div>
    );
  }

  return (
    <div className="max-w-2xl mx-auto py-8 space-y-6">
      <button
        onClick={() => navigate(-1)}
        className="inline-flex items-center gap-1.5 text-sm font-semibold text-bronze hover:text-walnut cursor-pointer"
      >
        <ArrowLeft size={16} /> Back
      </button>

      <Card className="p-8 space-y-8">
        <div className="border-b border-sand pb-4">
          <h1 className="text-2xl font-black text-walnut">Edit Profile</h1>
          <p className="text-xs text-bronze mt-1">Update your personal information & contact details.</p>
        </div>

        {/* Avatar Upload Section */}
        <div className="flex items-center gap-6 pb-6 border-b border-sand/40">
          <Avatar fallback={profile?.username || "U"} src={profile?.avatarUrl || undefined} size="lg" />
          <div className="space-y-2">
            <h3 className="text-xs font-bold uppercase tracking-wider text-walnut">Profile Picture</h3>
            <div className="flex items-center gap-3">
              <label className="cursor-pointer">
                <input
                  type="file"
                  accept="image/*"
                  onChange={handleAvatarUpload}
                  className="hidden"
                  disabled={uploadingAvatar}
                />
                <Button
                  type="button"
                  variant="secondary"
                  size="sm"
                  isLoading={uploadingAvatar}
                  leftIcon={<Upload size={14} />}
                >
                  Upload New
                </Button>
              </label>
              {profile?.avatarUrl && (
                <Button
                  type="button"
                  variant="ghost"
                  size="sm"
                  onClick={handleAvatarRemove}
                  leftIcon={<Trash2 size={14} />}
                  className="text-red-600 hover:text-red-700"
                >
                  Remove
                </Button>
              )}
            </div>
          </div>
        </div>

        {/* Profile Edit Form */}
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
          <Textarea
            label="Bio"
            placeholder="Tell the community about yourself..."
            error={errors.bio?.message}
            {...register("bio")}
          />

          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <Input
              label="Phone Number"
              type="text"
              placeholder="+1 (555) 000-0000"
              error={errors.phone?.message}
              {...register("phone")}
            />

            <Input
              label="Location"
              type="text"
              placeholder="e.g. San Francisco, CA"
              error={errors.location?.message}
              {...register("location")}
            />
          </div>

          <div className="flex justify-end gap-3 pt-6 border-t border-sand/40">
            <Button type="button" variant="ghost" onClick={() => navigate(-1)}>
              Cancel
            </Button>
            <Button
              type="submit"
              variant="primary"
              isLoading={isSubmitting || updateMutation.isPending}
              leftIcon={<Save size={16} />}
            >
              Save Changes
            </Button>
          </div>
        </form>
      </Card>
    </div>
  );
}
