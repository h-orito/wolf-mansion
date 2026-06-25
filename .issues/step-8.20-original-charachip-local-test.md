---
title: オリジナルキャラチップ村のローカルテスト環境整備
type: feat
status: open
step: 8.20
base: feature/monorepo-step8
---

## 目的

オリジナルキャラチップ村（参加者が自分でキャラ画像をアップロードする村）をローカル開発環境でテストできるようにする。現状は画像保存先・配信パスが本番前提 (`/var/www/html/wmansion/original`) のためローカルで動作確認できない。

## 現状の問題

1. **画像保存先が存在しない**: `app.original-image.basedir` が `/var/www/html/wmansion/original` (本番パス)。ローカルに作成されていない
2. **画像配信パスが通らない**: `app.original-image.baseurl` が `/wmansion/original`。backend の context-path (`/wolf-mansion-api`) 外のため backend が配信せず、Vite dev server にもプロキシ設定がない
3. **フィクスチャ村がない**: ローカル DB のフィクスチャ村 (5/6) にオリジナルキャラチップ村がなく、手動で村を作成する必要がある

## 作業内容

### 1. ローカル用画像保存・配信設定

- `application.yml` のローカル dev 設定で `basedir` を backend 配下の相対パス (例: `backend/data/original`) または `/tmp/wolf-mansion/original` に変更
- `baseurl` を backend が配信可能なパス (例: `/wolf-mansion-api/original-images`) に変更するか、Spring の ResourceHandler で `/wmansion/original/**` を配信する設定を追加
- Vite dev server の proxy 設定に画像配信パスを追加 (backend 経由で配信する場合)

### 2. 画像配信用 ResourceHandler (推奨案)

backend に `WebMvcConfigurer` を追加して `basedir` の画像を HTTP で配信する:
- パス: `baseurl` と一致するパス (例: `/wolf-mansion-api/original-images/**`)
- ソース: `basedir` のファイルシステム

### 3. 動作確認

- 新規村作成でオリジナルキャラチップを選択 → ダミーキャラ画像アップロード → 村作成成功
- debug パネルで allparticipate → 各参加者にプレースホルダー画像が割り当てられる
- 入村時にオリジナル画像アップロード → メッセージ表示でアップロード画像が表示される
- 表情差分追加 (RP パネル) で追加画像アップロード → 表情切替で表示される

## 依存

- Step 8.17 (debug パネル: allparticipate で参加者を自動追加)
- Step 8.6 (入村: オリジナル画像アップロード)

## 完了条件

- ローカル dev 環境でオリジナルキャラチップ村を作成し、画像アップロード → メッセージ表示まで一通り動作すること
