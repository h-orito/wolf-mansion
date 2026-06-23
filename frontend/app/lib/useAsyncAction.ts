import { useCallback, useState } from "react";

import { ApiError } from "~/lib/api";

export function useAsyncAction() {
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const execute = useCallback(
    async (action: () => Promise<void>, errorFallback: string) => {
      if (submitting) return;
      setSubmitting(true);
      setError(null);
      try {
        await action();
      } catch (e) {
        setError(e instanceof ApiError ? e.detail : errorFallback);
      } finally {
        setSubmitting(false);
      }
    },
    [submitting],
  );

  return { error, submitting, execute } as const;
}
