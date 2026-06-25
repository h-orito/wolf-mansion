import { Link } from "react-router";

export function UserPageLink({ name }: { name: string }) {
  return (
    <Link
      to={`/user/${encodeURIComponent(name)}`}
      target="_blank"
      className="text-wm-accent cursor-pointer hover:underline"
    >
      {name}
    </Link>
  );
}
