import { z } from "zod";

// zod は高速化のため new Function を使えるか試し、CSP (unsafe-eval 不許可) の違反として報告される。
// フォーム検証の規模では効果がないため使わせない。スキーマは zod を直接ではなくここから import する
z.config({ jitless: true });

export { z };
