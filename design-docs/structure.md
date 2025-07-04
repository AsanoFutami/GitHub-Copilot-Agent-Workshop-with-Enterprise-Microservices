# Ski Equipment Sales Shop - Microservices Architecture Diagram

## System Overview

This is an e-commerce platform specializing in ski equipment, built with microservices architecture.
It features flexible scaling capabilities to handle highly seasonal demand and provides a personalized shopping experience using AI.

## Architecture Diagram

```text
┌─────────────────────────────────────────────────────────────────────────┐
│                          Client Layer                                   │
├─────────────────────────────────────────────────────────────────────────┤
│  Web UI  │  Mobile App  │  Admin Panel  │  External Systems  │  API Clients │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                        API Gateway                                      │
│                      (Port: 8090)                                       │
│           • Routing  • Authentication  • Load Balancing  • Rate Limiting  │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                    ┌───────────────┼───────────────┐
                    │               │               │
                    ▼               ▼               ▼
┌─────────────┬─────────────┬─────────────┬─────────────┬─────────────┐
│Authentication│    User     │  Inventory  │    Sales    │ AI Support  │
│  Service    │ Management  │ Management  │ Management  │   Service   │
│             │   Service   │   Service   │   Service   │             │
│(Port: 8080) │(Port: 8081) │(Port: 8082) │(Port: 8083) │(Port: 8087) │
└─────────────┴─────────────┴─────────────┴─────────────┴─────────────┘
                    │               │               │
                    ▼               ▼               ▼
┌─────────────┬─────────────┬─────────────┬─────────────┐
│ Payment &   │   Point     │   Coupon    │  External   │
│ Cart Service│  Service    │  Service    │   Systems   │
│(Port: 8084) │(Port: 8085) │(Port: 8086) │             │
└─────────────┴─────────────┴─────────────┴─────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                    Data & Infrastructure Layer                          │
├─────────────┬─────────────┬─────────────┬─────────────┬─────────────┤
│ PostgreSQL  │    Redis    │Elasticsearch│    Kafka    │ Monitoring  │
│(Port: 5432) │(Port: 6379) │(Port: 9200) │(Port: 9092) │Prometheus   │
│  Main DB    │   Cache     │Search Engine│ Messaging   │& Grafana    │
└─────────────┴─────────────┴─────────────┴─────────────┴─────────────┘
```

## Microservices Detailed Configuration

### Core Services

#### 1. API Gateway (Port: 8090)

- **Role**: Unified access point, request routing
- **Features**:
  - Unified authentication and authorization control
  - Rate limiting and security
  - Load balancing and circuit breaker
- **Technology**: Spring Cloud Gateway

#### 2. Authentication Service (Port: 8080)

- **Role**: Authentication and authorization management
- **Features**:
  - JWT/OAuth 2.0/OpenID Connect
  - Multi-factor authentication (MFA)
  - Social login integration
  - Password reset
- **Database**: PostgreSQL
- **Technology**: Spring Security, OAuth 2.0

#### 3. User Management Service (Port: 8081)

- **Role**: User information management
- **Features**:
  - User profile management
  - Address book and shipping address management
  - User settings and permission management
- **Database**: PostgreSQL
- **Technology**: Spring Boot, JPA

### Business Services

#### 4. Inventory Management Service (Port: 8082)

- **Role**: Product and inventory management
- **Features**:
  - Product catalog management
  - Inventory quantity management and reservation
  - Category and tag management
  - Product images and metadata
- **Database**: PostgreSQL
- **Search**: Elasticsearch

#### 5. Sales Management Service (Port: 8083)

- **Role**: Order and sales management
- **Features**:
  - 注文処理・履歴管理
  - 配送状況追跡
  - 返品・交換処理
  - 売上分析・レポート
- **データベース**: PostgreSQL
- **メッセージング**: Kafka

#### 6. Payment & Cart Service (Port: 8084)

- **役割**: 決済・カート処理
- **機能**:
  - ショッピングカート管理
  - 決済処理（クレジット/デビットカード）
  - 決済ゲートウェイ連携
  - 決済履歴管理
- **データベース**: PostgreSQL
- **キャッシュ**: Redis

#### 7. Point Service (Port: 8085)

- **役割**: ポイント管理
- **機能**:
  - ポイント獲得・利用
  - ポイント履歴管理
  - ポイント有効期限管理
  - ポイント還元率設定
- **データベース**: PostgreSQL

#### 8. Coupon Service (Port: 8086)

- **役割**: クーポン管理
- **機能**:
  - クーポン作成・発行
  - 利用条件・制限管理
  - クーポン適用処理
  - 有効期限・利用回数管理
- **データベース**: PostgreSQL

#### 9. AI Support Service (Port: 8087)

- **役割**: AI推奨・検索・チャットボット
- **機能**:
  - 商品レコメンデーション
  - 高度な商品検索・フィルタリング
  - AIチャットボット
  - ユーザー行動分析
- **技術**: LangChain4j, OpenAI API
- **検索**: Elasticsearch

### インフラストラクチャサービス

#### データベース

- **PostgreSQL (Port: 5432)**: メインデータベース
- **Redis (Port: 6379)**: キャッシュ・セッション管理

#### メッセージング・検索

- **Apache Kafka (Port: 9092)**: イベントストリーミング・非同期処理
- **Elasticsearch (Port: 9200)**: 検索・分析エンジン

#### 監視・観測

- **Prometheus (Port: 9090)**: メトリクス収集
- **Grafana (Port: 3000)**: 可視化ダッシュボード

## 技術スタック

### 開発環境

```text
言語: Java 21 (LTS)
フレームワーク: Spring Boot 3.2.5, Spring Cloud 2023.0.1
ビルドツール: Maven 3.9+
コンテナ: Docker & Docker Compose
```

### データ・メッセージング

```text
データベース: PostgreSQL 15
キャッシュ: Redis 7
検索エンジン: Elasticsearch 8.12
メッセージング: Apache Kafka
```

### 監視・運用

```text
メトリクス: Prometheus
可視化: Grafana
ログ: ELK Stack
テスト: JUnit 5, Testcontainers
```

## プロジェクト構造

```text
java-skishop-microservices/
├── pom.xml                           # ルートPOM（マルチモジュール）
├── docker-compose.yml                # インフラ起動用
├── README.md                         # プロジェクト概要
├── all-endpoint-list.md              # 全API一覧
├── init-db.sql                       # データベース初期化
│
├── api-gateway/                      # APIゲートウェイ
│   ├── src/main/java/
│   └── pom.xml
│
├── authentication-service/           # 認証サービス
│   ├── src/main/java/
│   ├── init-auth-db.sql
│   └── pom.xml
│
├── user-management-service/          # ユーザー管理サービス
│   ├── src/main/java/
│   └── pom.xml
│
├── inventory-management-service/     # 在庫管理サービス
│   ├── src/main/java/
│   └── pom.xml
│
├── sales-management-service/         # 販売管理サービス
│   ├── src/main/java/
│   └── pom.xml
│
├── payment-cart-service/             # 決済・カートサービス
│   ├── src/main/java/
│   └── pom.xml
│
├── point-service/                    # ポイントサービス
│   ├── src/main/java/
│   └── pom.xml
│
├── coupon-service/                   # クーポンサービス
│   ├── src/main/java/
│   └── pom.xml
│
├── ai-support-service/               # AI支援サービス
│   ├── src/main/java/
│   ├── README_LangChain4j_Implementation.md
│   └── pom.xml
│
├── design-docs/                      # 設計ドキュメント
│   ├── spec.md                       # 仕様書
│   ├── strategy.md                   # 戦略
│   └── [service]-design.md           # 各サービス設計書
│
└── tmp/                              # 一時ファイル
    ├── IMPLEMENTATION_STATUS.md
    └── 各種実装ドキュメント
```

## サービス間通信

### 同期通信

- **HTTP/REST API**: リアルタイム処理が必要な操作
- **サービス間認証**: JWT token-based authentication

### 非同期通信

- **Apache Kafka**: イベントドリブンアーキテクチャ
- **イベント例**:
  - ユーザー登録完了 → ポイント付与
  - 注文確定 → 在庫減少、決済処理
  - 決済完了 → 配送手配、領収書発行

### データ連携

- **Redis**: セッション共有、カート状態
- **Elasticsearch**: 商品検索、ユーザー行動ログ

## セキュリティ構成

```text
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   Client    │───▶│ API Gateway │───▶│   Service   │
└─────────────┘    └─────────────┘    └─────────────┘
       │                    │                  │
       ▼                    ▼                  ▼
   HTTPS/TLS          JWT Validation    Service-to-Service
                      Rate Limiting         Auth (JWT)
```

### 認証フロー

1. クライアント → API Gateway (HTTPS)
2. API Gateway → Authentication Service (JWT検証)
3. 認証済みリクエスト → 各マイクロサービス
4. サービス間通信 (内部JWT)

## スケーリング戦略

### 水平スケーリング

- **ステートレス設計**: 全サービスがステートレス
- **ロードバランシング**: API Gateway経由
- **コンテナオーケストレーション**: Docker Compose/Kubernetes

### データベース戦略

- **サービス別DB分離**: マイクロサービスごとに専用DB
- **読み書き分離**: 読み取り専用レプリカ
- **キャッシュ活用**: Redis での高頻度アクセスデータ

## 運用・監視

### メトリクス収集

- **アプリケーションメトリクス**: Prometheus
- **ビジネスメトリクス**: カスタムメトリクス
- **インフラメトリクス**: システム監視

### ログ管理

- **構造化ログ**: JSON形式
- **相関ID**: リクエスト追跡
- **ログレベル**: ERROR, WARN, INFO, DEBUG

### ヘルスチェック

- **アプリケーションヘルスチェック**: Spring Boot Actuator
- **依存関係チェック**: DB, Redis, Kafka接続確認
- **カスケード障害対策**: サーキットブレーカーパターン

## 今後の拡張計画

- **マルチテナント対応**: 企業顧客向け機能
- **国際化対応**: 多言語・多通貨
- **モバイルアプリ**: React Native/Flutter
- **機械学習強化**: より高度なレコメンデーション
- **リアルタイム機能**: WebSocket, Server-Sent Events
