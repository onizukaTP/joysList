import React from "react";

function App() {
  return (
    <div className="min-h-screen bg-cream flex items-center justify-center p-4">
      <div className="text-center max-w-md bg-ivory border border-sand p-8 rounded-2xl shadow-md">
        <h1 className="text-4xl font-bold text-walnut mb-4">JoysList</h1>
        <p className="text-bronze text-base mb-8">
          A luxury, premium local marketplace built on React & Tailwind CSS.
        </p>
        <button className="bg-gold text-cream px-6 py-3 rounded-xl font-semibold hover:bg-gold-dark transition-colors duration-200 shadow-sm cursor-pointer">
          Get Started
        </button>
      </div>
    </div>
  );
}

export default App;
