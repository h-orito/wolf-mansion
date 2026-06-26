import { Alert } from "~/components/ui/Alert";

export function RenewalBanner() {
  return (
    <div className="px-[15px]">
      <Alert variant="warning">
        リニューアルテスト中につき、正常動作しない可能性があります。いつものサイトを利用する場合は{" "}
        <a
          href="https://wolfort.net/wolf-mansion/"
          target="_blank"
          rel="noreferrer"
          className="underline hover:opacity-80"
        >
          https://wolfort.net/wolf-mansion/
        </a>{" "}
        をご利用ください
      </Alert>
    </div>
  );
}
