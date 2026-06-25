import { useEffect, useMemo, useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router";

import { Button } from "~/components/ui/Button";
import { Divider } from "~/components/ui/Divider";
import { FormActions } from "~/components/ui/Form";
import { Heading } from "~/components/ui/Heading";
import { PageLayout } from "~/components/layout/PageLayout";
import { RequireAuth } from "~/features/auth/RequireAuth";
import type { SimpleSkillView } from "~/features/skills/api";
import { useSkillList } from "~/features/skills/useSkillList";
import { createVillage, fetchVillageSetting } from "~/features/villages/api";
import { ApiError } from "~/lib/api";
import { siteMeta } from "~/lib/meta";
import { zodResolver } from "~/lib/zodResolver";
import { BasicSection } from "~/features/village-form/BasicSection";
import { CharachipSection } from "./CharachipSection";
import { ConfirmModal } from "./ConfirmModal";
import { toCreateRequest } from "./createRequest";
import { DetailRuleSection } from "~/features/village-form/DetailRuleSection";
import { toDivertValues } from "./divert";
import { DivertSection } from "./DivertSection";
import { RequiredAfterCreationMark } from "~/features/village-form/fields";
import {
  RelativesSection,
  RpSection,
  SpecialRuleSection,
  SpectateSection,
} from "~/features/village-form/OtherSections";
import {
  createDefaultCampAllocations,
  createDefaultValues,
  type NewVillageFormInput,
  newVillageSchema,
} from "~/features/village-form/schema";
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
  const navigate = useNavigate();
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

  const [confirmValues, setConfirmValues] = useState<NewVillageFormInput | null>(null);
  const [originalImageFile, setOriginalImageFile] = useState<File | null>(null);
  const [creating, setCreating] = useState(false);
  const [createError, setCreateError] = useState<string | null>(null);
  const [diverting, setDiverting] = useState(false);
  const [divertError, setDivertError] = useState<string | null>(null);
  // 流用などプログラム的にフォームを書き換えた後、ダミーキャラ由来の項目 (画像・名前・
  // 略称・既定発言) を確認ダイアログなしで同期し直すためのキー
  const [charaSyncKey, setCharaSyncKey] = useState(0);

  // オリジナル画像のプレビュー URL。選び直したら古い URL を破棄する
  const originalImageUrl = useMemo(
    () => (originalImageFile ? URL.createObjectURL(originalImageFile) : null),
    [originalImageFile],
  );
  useEffect(() => {
    return () => {
      if (originalImageUrl) URL.revokeObjectURL(originalImageUrl);
    };
  }, [originalImageUrl]);

  const openConfirm = form.handleSubmit((values) => {
    setCreateError(null);
    setConfirmValues(values);
  });

  const divert = async (villageId: number) => {
    setDiverting(true);
    setDivertError(null);
    try {
      const setting = await fetchVillageSetting(villageId);
      form.reset(toDivertValues(setting, skills, new Date()));
      setOriginalImageFile(null);
      setCharaSyncKey((key) => key + 1);
    } catch {
      setDivertError("村設定の取得に失敗しました。時間をおいて再度お試しください。");
    } finally {
      setDiverting(false);
    }
  };

  const create = async () => {
    if (!confirmValues) return;
    setCreating(true);
    setCreateError(null);
    try {
      const village = await createVillage(
        toCreateRequest(confirmValues),
        confirmValues.shouldOriginalImage ? originalImageFile : null,
      );
      navigate(`/village/${village.id}`);
    } catch (e) {
      setCreateError(
        e instanceof ApiError
          ? e.detail
          : "村の作成に失敗しました。時間をおいて再度お試しください。",
      );
    } finally {
      setCreating(false);
    }
  };

  return (
    <PageLayout>
      <div className="px-[15px] pb-[10px]">
        <Heading>村作成</Heading>
        <p>
          <RequiredAfterCreationMark /> のついている項目は村作成後に変更できません。
        </p>
        <FormProvider {...form}>
          <DivertSection diverting={diverting} errorMessage={divertError} onDivert={divert} />
          <form noValidate onSubmit={openConfirm}>
            <BasicSection nowYear={nowYear} />
            <CharachipSection
              charaSyncKey={charaSyncKey}
              originalImageFile={originalImageFile}
              originalImageUrl={originalImageUrl}
              onSelectOriginalImage={setOriginalImageFile}
            />
            <DetailRuleSection skills={skills} defaultCamps={defaultCamps} />
            <SpectateSection />
            <RelativesSection />
            <SpecialRuleSection skills={skills} />
            <RpSection />
            <Divider />
            <FormActions>
              <Button type="submit">確認画面へ</Button>
            </FormActions>
          </form>
          <ConfirmModal
            open={confirmValues !== null}
            values={confirmValues}
            originalImageUrl={originalImageUrl}
            creating={creating}
            errorMessage={createError}
            onBack={() => {
              setConfirmValues(null);
            }}
            onCreate={create}
          />
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
