import { createContext, useContext } from "react";

import type { VillageDetailView } from "~/features/village/api";

const VillageContext = createContext<VillageDetailView | null>(null);

export const VillageProvider = VillageContext.Provider;

export function useVillageContext(): VillageDetailView {
  const village = useContext(VillageContext);
  if (village == null) throw new Error("useVillageContext must be used within VillageProvider");
  return village;
}

export function useVillageId(): number {
  return useVillageContext().id;
}
