import React, { useState } from "react";
import { Search, MapPin, Grid } from "lucide-react";
import Button from "../ui/Button";

interface SearchBarProps {
  initialKeyword?: string;
  initialLocation?: string;
  onSearch: (keyword: string, location: string) => void;
}

export default function SearchBar({
  initialKeyword = "",
  initialLocation = "",
  onSearch,
}: SearchBarProps) {
  const [keyword, setKeyword] = useState(initialKeyword);
  const [location, setLocation] = useState(initialLocation);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSearch(keyword.trim(), location.trim());
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="w-full bg-ivory border border-sand rounded-2xl p-2 shadow-md flex flex-col md:flex-row items-center gap-2"
    >
      {/* Keyword input */}
      <div className="flex-grow w-full flex items-center gap-2 px-3 py-2 border-b md:border-b-0 md:border-r border-sand">
        <Search className="text-sand-dark shrink-0" size={20} />
        <input
          type="text"
          placeholder="What are you looking for?"
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          className="w-full bg-transparent text-coffee placeholder:text-sand-dark focus:outline-none text-sm md:text-base"
        />
      </div>

      {/* Location input */}
      <div className="flex-grow w-full flex items-center gap-2 px-3 py-2">
        <MapPin className="text-sand-dark shrink-0" size={20} />
        <input
          type="text"
          placeholder="Location (city, state, zip)..."
          value={location}
          onChange={(e) => setLocation(e.target.value)}
          className="w-full bg-transparent text-coffee placeholder:text-sand-dark focus:outline-none text-sm md:text-base"
        />
      </div>

      {/* Submit Button */}
      <Button
        type="submit"
        variant="primary"
        size="md"
        className="w-full md:w-auto px-8"
      >
        Search
      </Button>
    </form>
  );
}
