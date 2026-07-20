import React from "react";
import { Link } from "react-router";
import Button from "../components/ui/Button";

export default function NotFoundPage() {
  return (
    <div className="flex-1 flex flex-col items-center justify-center py-20 px-4 text-center">
      <h1 className="text-9xl font-black text-sand-dark tracking-widest select-none">
        404
      </h1>
      <h2 className="text-2xl font-bold text-walnut mt-4 mb-2">
        Page Not Found
      </h2>
      <p className="text-bronze max-w-md mb-8">
        The page you are looking for might have been removed, had its name changed, or is temporarily unavailable.
      </p>
      <Link to="/">
        <Button variant="primary">Go Home</Button>
      </Link>
    </div>
  );
}
