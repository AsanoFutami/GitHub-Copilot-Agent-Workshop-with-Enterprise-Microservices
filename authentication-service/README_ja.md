# 認証サービス

スキーショップECプラットフォームのID・アクセス管理サービス

## 主な提供機能

### 機能一覧

- **セキュア認証**: Microsoft Entra ID連携によるユーザーログイン・登録
- **JWTトークン管理**: APIアクセス用の安全なトークン発行・検証
- **多要素認証**: TOTPベースのMFA対応
- **ロールベースアクセス制御**: 柔軟なRBAC（ロール階層対応）
- **セッション管理**: Redisによる安全なセッション管理
- **パスワード管理**: パスワードリセット・変更・安全な保存
- **OAuth2/OpenID Connect**: 外部IDプロバイダー連携
- **セキュリティ監視**: 監査ログ・不審活動検知
- **アカウント保護**: レートリミット・アカウントロック・セキュリティポリシー

## サービスエンドポイント

| HTTPメソッド | エンドポイント | 説明 | 認証 |
|-------------|----------|-------------|-------|
| POST | `/api/v1/auth/login` | 資格情報によるログイン | 公開 |
| POST | `/api/v1/auth/refresh` | アクセストークン再発行 | 認証要 |
| POST | `/api/v1/auth/logout` | ログアウト | 認証要 |
| POST | `/api/v1/auth/validate` | トークン検証 | 認証要 |
| GET | `/api/v1/auth/me` | 現在のユーザー情報取得 | 認証要 |
| POST | `/api/v1/auth/mfa/verify` | MFAコード検証 | 公開 |
| POST | `/api/v1/auth/mfa/setup` | MFA設定 | 認証要 |
| DELETE | `/api/v1/auth/mfa/disable` | MFA無効化 | 認証要 |
| POST | `/api/v1/auth/password/reset` | パスワードリセット申請 | 公開 |
| POST | `/api/v1/auth/password/confirm` | パスワードリセット確定 | 公開 |
| PUT | `/api/v1/auth/password/change` | パスワード変更 | 認証要 |
| POST | `/api/auth/users` | 新規ユーザー登録 | 公開 |
| DELETE | `/api/auth/users/{userId}` | ユーザー論理削除 | 管理者認証要 |
| GET | `/oauth2/authorization/azure` | Azure AD OAuth2開始 | 公開 |
| GET | `/login/oauth2/code/azure` | OAuth2コールバック | 公開 |
| GET | `/actuator/health` | ヘルスチェック | 公開 |
| GET | `/actuator/prometheus` | Prometheusメトリクス | 管理者認証要 |

## 技術スタック

- **Java**: 21 LTS
- **Spring Boot**: 3.5.3
- **Spring Security**: OAuth2, JWT
- **Spring Data JPA**: DBアクセス
- **Spring Data Redis**: セッション管理
- **Microsoft Entra ID**: Azure AD連携
- **データベース**: PostgreSQL 16+
- **キャッシュ**: Redis 7.2+
- **イベントストリーミング**: Apache Kafka / Azure Service Bus
- **コンテナ**: Docker
- **クラウド**: Azure Container Apps

## 環境変数

| 変数名 | 説明 | デフォルト値 |
|--------|------|--------------|
| `SPRING_PROFILES_ACTIVE` | Springプロファイル | `local` |
| `DB_URL` | PostgreSQL接続URL | `jdbc:postgresql://localhost:5432/skishop_auth` |
