import { useMemo, useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router";
import { useQuery } from "@tanstack/react-query";

import { Button, LinkButton } from "~/components/ui/Button";

import { Divider } from "~/components/ui/Divider";
import { FormActions } from "~/components/ui/Form";
import { Heading } from "~/components/ui/Heading";
import { PageLayout } from "~/components/layout/PageLayout";
import { RequireAuth } from "~/features/auth/RequireAuth";
import type { SimpleSkillView } from "~/features/skills/api";
import { useSkillList } from "~/features/skills/useSkillList";
import { fetchVillageSettingForUpdate, updateVillageSetting } from "~/features/village/api";
import { fetchVillageSetting } from "~/features/villages/api";
import { BasicSection } from "~/features/village-form/BasicSection";
import { DetailRuleSection } from "~/features/village-form/DetailRuleSection";
import { RequiredAfterCreationMark } from "~/features/village-form/fields";
import {
  RelativesSection,
  RpSection,
  SpecialRuleSection,
  SpectateSection,
} from "~/features/village-form/OtherSections";
import {
  createDefaultCampAllocations,
  type NewVillageFormInput,
  villageSettingsSchema,
} from "~/features/village-form/schema";
import { toSettingsFormValues, toUpdateRequest } from "~/features/village-form/settingsForm";
import { ApiError } from "~/lib/api";
import { siteMeta } from "~/lib/meta";
import { zodResolver } from "~/lib/zodResolver";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("村設定変更");
}

function VillageSettingsPage({ villageId }: { villageId: number }) {
  const { data: skillData, isLoading: skillsLoading } = useSkillList();
  const {
    data: setting,
    isLoading: settingLoading,
    error: settingError,
  } = useQuery({
    queryKey: ["village-setting-for-update", villageId],
    queryFn: () => fetchVillageSettingForUpdate(villageId),
    retry: false,
  });
  // 村作成後に変更できない項目の現在値表示用 (変更フォームのレスポンスには含まれない)
  const { data: publicSetting } = useQuery({
    queryKey: ["village-setting", villageId],
    queryFn: () => fetchVillageSetting(villageId),
    retry: false,
  });

  if (skillsLoading || settingLoading) {
    return (
      <PageLayout>
        <p className="p-4">読み込み中...</p>
      </PageLayout>
    );
  }

  if (settingError != null) {
    const message =
      settingError instanceof ApiError
        ? settingError.detail
        : "村設定の取得に失敗しました。村建てプレイヤーのみ変更できます。";
    return (
      <PageLayout>
        <div className="px-[15px] py-[30px]">{message}</div>
      </PageLayout>
    );
  }

  if (!skillData || !setting) {
    return (
      <PageLayout>
        <p className="p-4">データの取得に失敗しました。時間をおいて再度お試しください。</p>
      </PageLayout>
    );
  }

  return (
    <VillageSettingsForm
      villageId={villageId}
      skills={skillData.skills}
      setting={setting}
      fixedSkillRequest={publicSetting?.rule.isPossibleSkillRequest ?? true}
      fixedCreatorIsProducer={publicSetting?.rule.isCreatorIsProducer ?? false}
      isOriginalCharachip={publicSetting?.chara.isOriginalCharachip ?? false}
    />
  );
}

function VillageSettingsForm({
  villageId,
  skills,
  setting,
  fixedSkillRequest,
  fixedCreatorIsProducer,
  isOriginalCharachip,
}: {
  villageId: number;
  skills: SimpleSkillView[];
  setting: ReturnType<typeof fetchVillageSettingForUpdate> extends Promise<infer T> ? T : never;
  fixedSkillRequest: boolean;
  fixedCreatorIsProducer: boolean;
  isOriginalCharachip: boolean;
}) {
  const navigate = useNavigate();
  const nowYear = useMemo(() => new Date().getFullYear(), []);
  const defaultCamps = useMemo(() => createDefaultCampAllocations(skills), [skills]);
  const initialValues = useMemo(
    () => toSettingsFormValues(setting, skills, isOriginalCharachip),
    [setting, skills, isOriginalCharachip],
  );

  const form = useForm<NewVillageFormInput>({
    resolver: zodResolver(villageSettingsSchema),
    defaultValues: initialValues,
    mode: "onTouched",
  });

  const [saving, setSaving] = useState(false);
  const [saveError, setSaveError] = useState<string | null>(null);

  const onSubmit = form.handleSubmit(async (values) => {
    setSaving(true);
    setSaveError(null);
    try {
      await updateVillageSetting(villageId, toUpdateRequest(values));
      navigate(`/village/${villageId}`);
    } catch (e) {
      setSaveError(
        e instanceof ApiError
          ? e.detail
          : "村設定の変更に失敗しました。時間をおいて再度お試しください。",
      );
    } finally {
      setSaving(false);
    }
  });

  return (
    <PageLayout>
      <div className="px-[15px] pb-[10px]">
        <Heading>村設定変更</Heading>
        <p>
          <RequiredAfterCreationMark /> のついている項目は村作成後に変更できません。
        </p>
        <FormProvider {...form}>
          <form noValidate onSubmit={onSubmit}>
            <BasicSection nowYear={nowYear} />
            <DetailRuleSection
              skills={skills}
              defaultCamps={defaultCamps}
              fixedSkillRequest={fixedSkillRequest}
            />
            <SpectateSection fixedCreatorIsProducer={fixedCreatorIsProducer} />
            <RelativesSection />
            <SpecialRuleSection skills={skills} />
            <RpSection />
            <Divider />
            {saveError && <p className="mb-[10px] whitespace-pre-line text-red-400">{saveError}</p>}
            <FormActions>
              <LinkButton variant="default" to={`/village/${villageId}`}>
                村画面へ戻る
              </LinkButton>
              <Button type="submit" disabled={saving}>
                {saving ? "変更中..." : "変更する"}
              </Button>
            </FormActions>
          </form>
        </FormProvider>
      </div>
    </PageLayout>
  );
}

export default function VillageSettings({ params }: Route.ComponentProps) {
  const villageId = Number(params.villageId);
  return (
    <RequireAuth>
      <VillageSettingsPage villageId={villageId} />
    </RequireAuth>
  );
}
