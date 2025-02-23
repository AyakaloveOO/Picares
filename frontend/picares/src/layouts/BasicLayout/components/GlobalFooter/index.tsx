import "./index.css";
import React from "react";

export default function GlobalFooter() {
  const year = new Date().getFullYear();
  return (
    <div className={"global-footer"}>
      <div>@ {year} Picares</div>
    </div>
  );
}
