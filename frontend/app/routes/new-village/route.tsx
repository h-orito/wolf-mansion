import { useMemo } from "react";
import { FormProvider, useForm } from "react-hook-form";

import { Button } from "~/components/ui/Button";
import { Divider } from "~/components/ui/Divider";
import { FormActions } from "~/components/ui/Form";
import { Heading } from "~/components/ui/Heading";
import { PageLayout } from "~/components/layout/PageLayout";
import { RequireAuth } from "~/features/auth/RequireAuth";
import type { SimpleSkillView } from "~/features/skills/api";
import { useSkillList } from "~/features/skills/useSkillList";
import { siteMeta } from "~/lib/meta";
import { zodResolver } from "~/lib/zodResolver";
import { BasicSection } from "./BasicSection";
import { DetailRuleSection } from "./DetailRuleSection";
import { RequiredAfterCreationMark } from "./fields";
import { RelativesSection, RpSection, SpecialRuleSection, SpectateSection } from "./OtherSections";
import {
  createDefaultCampAllocations,
  createDefaultValues,
  type NewVillageFormInput,
  newVillageSchema,
} from "./schema";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("村作成");
}

function NewVillagePage() {
  const { data, isLoading } = useSkillList();

  if (isLoading) {
    return (
      <PageLayout>
        <p className="p-4">読み込み中...</p>
      </PageLayout>
    );
  }
  if (!data) {
    return (
      <PageLayout>
        <p className="p-4">役職情報の取得に失敗しました。時間をおいて再度お試しください。</p>
      </PageLayout>
    );
  }
  return <NewVillageForm skills={data.skills} />;
}

function NewVillageForm({ skills }: { skills: SimpleSkillView[] }) {
  const [defaultValues, nowYear] = useMemo(() => {
    const now = new Date();
    return [createDefaultValues(skills, now), now.getFullYear()] as const;
  }, [skills]);
  const defaultCamps = useMemo(() => createDefaultCampAllocations(skills), [skills]);
  const form = useForm<NewVillageFormInput>({
    resolver: zodResolver(newVillageSchema),
    defaultValues,
    mode: "onTouched",
  });

  return (
    <PageLayout>
      <div className="px-[15px] pb-[10px]">
        <Heading>村作成</Heading>
        <p>
          <RequiredAfterCreationMark /> のついている項目は村作成後に変更できません。
        </p>
        <FormProvider {...form}>
          <form noValidate>
            <BasicSection nowYear={nowYear} />
            <DetailRuleSection skills={skills} defaultCamps={defaultCamps} />
            <SpectateSection />
            <RelativesSection />
            <SpecialRuleSection />
            <RpSection />
            <Divider />
            <FormActions>
              {/* 確認モーダル → 作成は未実装。実装まで押せないようにしておく */}
              <Button type="submit" disabled>
                確認画面へ
              </Button>
            </FormActions>
          </form>
        </FormProvider>
      </div>
    </PageLayout>
  );
}

export default function NewVillage() {
  return (
    <RequireAuth>
      <NewVillagePage />
    </RequireAuth>
  );
}
