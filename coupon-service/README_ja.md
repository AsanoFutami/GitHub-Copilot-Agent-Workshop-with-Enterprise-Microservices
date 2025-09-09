# クーポンサービス

スキーショップECプラットフォームのプロモーション・割引管理サービス

## 主な提供機能

### 機能一覧

- **クーポン管理**: クーポンの作成・検証・利用・ライフサイクル管理
- **キャンペーン管理**: プロモーションキャンペーンの作成・スケジューリング・モニタリング
- **配布エンジン**: 対象ユーザーへの自動・手動クーポン配布
- **ルールエンジン**: 複雑なビジネスルールによるクーポン適格判定
- **利用状況トラッキング**: リアルタイム利用分析・不正検知
- **分析・レポート**: キャンペーン効果分析・最適化インサイト
- **一括発行**: 複数クーポンの効率的な一括生成
- **多様な形式対応**: 割引率・定額・送料無料・BOGO（1つ買うと1つ無料）など

## サービスエンドポイント

| HTTPメソッド | エンドポイント | 説明 | 認証 |
|-------------|----------|-------------|-------|
| POST | `/api/v1/coupons` | クーポン作成 | 管理者 |
| GET | `/api/v1/coupons` | キャンペーン別クーポン一覧取得 | 管理者 |
| GET | `/api/v1/coupons/{code}` | コード指定クーポン取得 | 公開 |
| POST | `/api/v1/coupons/validate` | クーポン検証 | 認証要 |
| POST | `/api/v1/coupons/redeem` | クーポン利用 | 認証要 |
| GET | `/api/v1/coupons/usage/{couponId}` | クーポン利用状況取得 | 管理者 |
| POST | `/api/v1/coupons/bulk-generate` | 一括クーポン生成 | 管理者 |
| GET | `/api/v1/coupons/user/available` | ユーザー利用可能クーポン取得 | 認証要 |
| POST | `/api/v1/campaigns` | キャンペーン作成 | 管理者 |
| GET | `/api/v1/campaigns` | キャンペーン一覧取得 | 管理者 |
| GET | `/api/v1/campaigns/{campaignId}` | キャンペーン詳細取得 | 管理者 |
| PUT | `/api/v1/campaigns/{campaignId}` | キャンペーン更新 | 管理者 |
| POST | `/api/v1/campaigns/{campaignId}/activate` | キャンペーン有効化 | 管理者 |
| GET | `/api/v1/campaigns/{campaignId}/analytics` | キャンペーン分析 | 管理者 |
| GET | `/api/v1/campaigns/active` | 有効キャンペーン一覧取得 | 管理者 |
| GET | `/api/v1/distributions/rules/{campaignId}` | 配布ルール取得 | 管理者 |
| POST | `/api/v1/distributions/rules/{campaignId}` | 配布ルール作成 | 管理者 |
| PUT | `/api/v1/distributions/rules/{ruleId}` | 配布ルール更新 | 管理者 |
| DELETE | `/api/v1/distributions/rules/{ruleId}` | 配布ルール削除 | 管理者 |
| POST | `/api/v1/distributions/execute/{campaignId}` | クーポン配布実行 | 管理者 |
| GET | `/api/v1/distributions/history/{campaignId}` | 配布履歴取得 | 管理者 |

## 技術スタック

- **Java**: 21 LTS
- **Spring Boot**: 3.5.3
- **Spring Data JPA**: DBアクセス
- **Spring Data Redis**: キャッシュ・レートリミット
- **Spring Cloud Stream**: Kafkaによるイベント処理
- **Quartz Scheduler**: ジョブスケジューリング
- **データベース**: PostgreSQL 16+
- **キャッシュ**: Redis 7.2+
- **イベントストリーミング**: Apache Kafka
- **Flyway**: DBマイグレーション
- **MapStruct**: オブジェクトマッピング
- **コンテナ**: Docker
- **クラウド**: Azure Container Apps
