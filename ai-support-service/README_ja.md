# AIサポートサービス

スキーショップECプラットフォーム向けのAI活用サポート機能

## 主な提供機能

### 機能一覧

- **AIチャットサポート**: Azure OpenAIとLangChain4jを活用したインテリジェントなチャットボットカスタマーサービス
- **商品レコメンデーション**: ユーザーの行動や嗜好に基づくパーソナライズされた商品推薦
- **セマンティック検索**: 自然言語処理による高度な検索機能
- **検索強化**: クエリ自動補正やオートコンプリート提案
- **ビジュアル検索**: 画像を用いた商品検索機能
- **分析・インサイト**: ユーザー行動分析や推薦理由の説明

## サービスエンドポイント

| HTTPメソッド | エンドポイント | 説明 | 認証 |
|-------------|----------|-------------|---------------|
| POST | `/api/v1/chat/message` | チャットメッセージ送信 | 認証要 |
| POST | `/api/v1/chat/recommend` | チャットレコメンド | 認証要 |
| POST | `/api/v1/chat/advice` | チャットアドバイス | 認証要 |
| GET | `/api/v1/chat/conversations/{userId}` | 会話履歴取得 | 認証要 |
| DELETE | `/api/v1/chat/conversations/{conversationId}` | 会話履歴削除 | 認証要 |
| POST | `/api/v1/chat/feedback` | チャットフィードバック | 認証要 |
| GET | `/api/v1/recommendations/{userId}` | ユーザー推薦取得 | 認証要 |
| GET | `/api/v1/recommendations/similar/{productId}` | 類似商品推薦 | 公開 |
| GET | `/api/v1/recommendations/trending` | トレンド商品 | 公開 |
| GET | `/api/v1/recommendations/category/{category}` | カテゴリ推薦 | 公開 |
| POST | `/api/v1/recommendations/feedback` | 推薦フィードバック | 認証要 |
| GET | `/api/v1/recommendations/explain/{userId}/{productId}` | 推薦理由説明 | 認証要 |
| POST | `/api/v1/search/semantic` | セマンティック検索 | 公開 |
| GET | `/api/v1/search/autocomplete` | オートコンプリート | 公開 |
| GET | `/api/v1/search/suggest` | 検索サジェスト | 公開 |
| POST | `/api/v1/search/visual` | ビジュアル検索 | 公開 |

## 技術スタック

- **Java**: 21 LTS
- **Spring Boot**: 3.5.3
- **LangChain4j**: 1.1.0（Azure OpenAI連携）
- **Azure OpenAI Service**: GPT-4o, GPT-3.5-turbo, text-embedding-3-small
- **データベース**: MongoDB（会話履歴・ユーザー嗜好）
- **キャッシュ**: Redis（推薦キャッシュ・セッション管理）
- **ベクターデータベース**: PineconeまたはAzure Cognitive Search
- **イベントストリーミング**: Apache Kafka
- **コンテナ**: Docker
- **クラウド**: Azure Container Apps

## 環境変数

| 変数名 | 説明 | デフォルト値 |
|--------|------|--------------|
| `AZURE_OPENAI_KEY` | Azure OpenAI APIキー | - |
| `AZURE_OPENAI_ENDPOINT` | Azure OpenAIエンドポイントURL | `https://your-resource.openai.azure.com/` |
| `AZURE_OPENAI_DEPLOYMENT_NAME` | チャットモデルのデプロイ名 | `gpt-4o` |
| `AZURE_OPENAI_EMBEDDING_DEPLOYMENT_NAME` | 埋め込みモデルのデプロイ名 | `text-embedding-3-small` |
| `MONGODB_CONNECTION_STRING` | MongoDB接続文字列 | `mongodb://localhost:27017/ai_support_db` |
| `REDIS_HOST` | Redisホスト | `localhost` |
| `REDIS_PORT` | Redisポート | `6379` |
