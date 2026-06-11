import { useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router";

import { Button, LinkButton } from "~/components/ui/Button";
import { fieldErrorClass, FormRow, formErrorClass } from "~/components/ui/Form";
import { Heading } from "~/components/ui/Heading";
import { textareaClass } from "~/components/ui/Input";
import { TextLink } from "~/components/ui/TextLink";
import { PageLayout } from "~/components/layout/PageLayout";
import { useMe } from "~/features/auth/useMe";
import {
  deleteRandomKeyword,
  type RandomKeyword,
  updateRandomKeyword,
} from "~/features/random-keywords/api";
import { randomKeywordErrorMessage } from "~/features/random-keywords/errorMessage";
import { RandomKeywordNotes } from "~/features/random-keywords/RandomKeywordNotes";
import {
  type RandomKeywordEditInput,
  randomKeywordEditSchema,
  splitMessages,
} from "~/features/random-keywords/schema";
import {
  useInvalidateRandomKeywords,
  useRandomKeyword,
} from "~/features/random-keywords/useRandomKeywords";
import { ApiError } from "~/lib/api";
import { siteMeta } from "~/lib/meta";
import { zodResolver } from "~/lib/zodResolver";
import type { Route } from "./+types/route";

export function meta(_: Route.MetaArgs) {
  return siteMeta("ランダムメッセージ編集");
}

function EditForm({ keyword }: { keyword: RandomKeyword }) {
  const navigate = useNavigate();
  const invalidate = useInvalidateRandomKeywords();
  const [formError, setFormError] = useState<string | null>(null);
  const [isDeleting, setIsDeleting] = useState(false);
  // 閲覧は公開のまま (書き込みのみログイン必須) なので、未ログインでもフォームは表示し誘導だけ出す
  const { me, isLoading: isMeLoading } = useMe();

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<RandomKeywordEditInput>({
    resolver: zodResolver(randomKeywordEditSchema),
    defaultValues: { message: keyword.contents.map((content) => content.message).join("\n") },
  });

  const onSubmit = handleSubmit(async (values) => {
    setFormError(null);
    try {
      await updateRandomKeyword(keyword.id, splitMessages(values.message));
      await invalidate();
      navigate("/random-message");
    } catch (e) {
      setFormError(randomKeywordErrorMessage(e));
    }
  });

  const onDelete = async () => {
    if (!confirm("本当に削除してよろしいですか？")) return;
    setFormError(null);
    setIsDeleting(true);
    try {
      await deleteRandomKeyword(keyword.id);
      await invalidate();
      navigate("/random-message");
    } catch (e) {
      setFormError(randomKeywordErrorMessage(e));
    } finally {
      setIsDeleting(false);
    }
  };

  return (
    <>
      <RandomKeywordNotes />
      {!isMeLoading && !me && (
        <p className="mb-2">
          編集・削除には
          <TextLink to={`/login?returnTo=${encodeURIComponent(`/random-keyword/${keyword.id}`)}`}>
            ログイン
          </TextLink>
          が必要です。
        </p>
      )}
      <form onSubmit={onSubmit} noValidate>
        {formError && <span className={formErrorClass}>{formError}</span>}
        <FormRow label="キーワード" labelWidth="wide">
          <p className="pt-[5px]">{keyword.keyword}</p>
        </FormRow>
        <FormRow label="変換後文字列（改行区切り）" htmlFor="message" labelWidth="wide">
          <textarea id="message" rows={10} className={textareaClass} {...register("message")} />
          {errors.message && <p className={fieldErrorClass}>{errors.message.message}</p>}
        </FormRow>
        <div className="flex items-center gap-[5px]">
          <LinkButton to="/random-message" variant="default">
            戻る
          </LinkButton>
          <Button variant="danger" onClick={onDelete} disabled={isDeleting}>
            削除
          </Button>
          <Button type="submit" className="ml-auto" disabled={isSubmitting}>
            登録
          </Button>
        </div>
      </form>
    </>
  );
}

export default function RandomKeywordEdit({ params }: Route.ComponentProps) {
  const id = Number(params.id);
  const isValidId = Number.isInteger(id);
  const { data: keyword, error } = useRandomKeyword(id, isValidId);
  const isNotFound = !isValidId || (error instanceof ApiError && error.status === 404);

  return (
    <PageLayout>
      <div className="px-[15px] pb-[10px]">
        <Heading>ランダムメッセージ編集</Heading>
        {isNotFound && (
          <div>
            <p className="mb-[10px]">すでに削除されています</p>
            <LinkButton to="/random-message" variant="default">
              戻る
            </LinkButton>
          </div>
        )}
        {error && !isNotFound && (
          <p className={formErrorClass}>{randomKeywordErrorMessage(error)}</p>
        )}
        {keyword && <EditForm keyword={keyword} />}
      </div>
    </PageLayout>
  );
}
