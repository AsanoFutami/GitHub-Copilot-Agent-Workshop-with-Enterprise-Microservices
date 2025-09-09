# フロントエンドサービス

スキーショップECプラットフォームのWeb UIインターフェース

## 主な提供機能

### 機能一覧

- **レスポンシブWebインターフェース**: Bootstrap 5を用いたモダンなレスポンシブUI
- **ユーザー認証**: ログイン・登録・プロフィール管理
- **商品閲覧**: 商品の一覧・検索・カテゴリ・属性による絞り込み
- **ショッピングカート**: カートへの追加・更新・削除
- **購入手続き**: 決済連携による注文完了
- **注文管理**: 注文履歴の閲覧・注文状況の確認
- **AIチャット連携**: 商品レコメンド付きインタラクティブチャットサポート
- **ユーザープロファイル**: 個人情報・住所・嗜好の管理
- **管理者ダッシュボード**: 在庫・注文管理用の管理画面

## サービスエンドポイント

| HTTPメソッド | エンドポイント | 説明 | 認証 |
|-------------|----------|-------------|-------|
| GET | `/` | ホームページ | 公開 |
| GET | `/products` | 商品一覧ページ | 公開 |
| GET | `/products/{id}` | 商品詳細ページ | 公開 |
| GET | `/cart` | カートページ | 公開 |
| POST | `/cart/add` | カートに商品追加 | 公開 |
| PUT | `/cart/update` | カートアイテム更新 | 公開 |
| DELETE | `/cart/remove` | カートアイテム削除 | 公開 |
| GET | `/checkout` | 購入手続きページ | 認証要 |
| POST | `/checkout` | 注文処理 | 認証要 |
| GET | `/profile` | ユーザープロファイルページ | 認証要 |
| GET | `/orders` | 注文履歴 | 認証要 |
| GET | `/orders/{id}` | 注文詳細 | 認証要 |
| GET | `/chat` | AIチャット画面 | 公開 |
| POST | `/api/chat/send` | チャットメッセージ送信 | 公開 |
| GET | `/admin` | 管理者ダッシュボード | 管理者 |
| GET | `/admin/products` | 商品管理 | 管理者 |
| GET | `/admin/orders` | 注文管理 | 管理者 |
| PUT | `/admin/orders/{id}/status` | 注文ステータス更新 | 管理者 |
| GET | `/admin/customers` | 顧客管理 | 管理者 |
| GET | `/admin/reports` | レポート画面 | 管理者 |

## 技術スタック

- **Java**: 21 LTS
- **Spring Boot**: 3.5.3
- **Thymeleaf**: サーバーサイドテンプレートエンジン
- **Bootstrap**: 5.3.2（WebJars）
- **jQuery**: 3.7.1（WebJars）
- **Font Awesome**: 6.4.0（WebJars）
- **Spring Security**: OAuth2クライアント連携
- **Spring WebFlux**: WebClientによるAPI呼び出し
- **Caffeine**: 高速キャッシュ
- **Apache HttpClient**: API呼び出し用HTTPクライアント
- **Docker**: コンテナデプロイ
- **クラウド**: Azure Container Apps
