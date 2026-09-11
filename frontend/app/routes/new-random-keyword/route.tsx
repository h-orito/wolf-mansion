import { useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router";

import { Button } from "~/components/ui/Button";
import { fieldErrorClass, FormActions, formErrorClass, FormRow } from "~/components/ui/Form";
import { Heading } from "~/components/ui/Heading";
import { inputClass, textareaClass } from "~/components/ui/Input";
import { PageLayout } from "~/components/layout/PageLayout";
import { RequireAuth } from "~/features/auth/RequireAuth";
import { registerRandomKeyword } from "~/features/random-keywords/api";
import { randomKeywordErrorMessage } from "~/features/random-keywords/errorMessage";
import { RandomKeywordNotes } from "~/features/random-keywords/RandomKeywordNotes";
import {
  type RandomKeywordCreateInput,
  randomKeywordCreateSchema,
  splitMessages,
} from "~/features/random-keywords/schema";
import { useInvalidateRandomKeywords } from "~/features/random-keywords/useRandomKeywords";
import { siteMeta } from "~/lib/meta";
import { zodResolver } from "~/lib/zodResolver";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("ランダムメッセージ作成");
}

function CreateForm() {
  const navigate = useNavigate();
  const invalidate = useInvalidateRandomKeywords();
  const [formError, setFormError] = useState<string | null>(null);

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<RandomKeywordCreateInput>({ resolver: zodResolver(randomKeywordCreateSchema) });

  const onSubmit = handleSubmit(async (values) => {
    setFormError(null);
    try {
      await registerRandomKeyword({
        keyword: values.keyword,
        messages: splitMessages(values.message),
      });
      await invalidate();
      navigate("/random-message");
    } catch (e) {
      setFormError(randomKeywordErrorMessage(e));
    }
  });

  return (
    <PageLayout>
      <div className="px-[15px] pb-[10px]">
        <Heading>ランダムメッセージ作成</Heading>
        <RandomKeywordNotes />
        <form onSubmit={onSubmit} noValidate>
          {formError && <span className={formErrorClass}>{formError}</span>}
          <FormRow label="キーワード" htmlFor="keyword" labelWidth="wide">
            <input id="keyword" type="text" className={inputClass} {...register("keyword")} />
            {errors.keyword && <p className={fieldErrorClass}>{errors.keyword.message}</p>}
          </FormRow>
          <FormRow label="変換後文字列（改行区切り）" htmlFor="message" labelWidth="wide">
            <textarea id="message" rows={10} className={textareaClass} {...register("message")} />
            {errors.message && <p className={fieldErrorClass}>{errors.message.message}</p>}
          </FormRow>
          <FormActions>
            <Button type="submit" disabled={isSubmitting}>
              登録
            </Button>
          </FormActions>
        </form>
      </div>
    </PageLayout>
  );
}

export default function NewRandomKeyword() {
  return (
    <RequireAuth>
      <CreateForm />
    </RequireAuth>
  );
}
