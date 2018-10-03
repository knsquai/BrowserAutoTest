# BrowserAutoTest
## アプリケーションの概要
* Seleniumを利用し、異なるブラウザでのテストを容易に実行するアプリケーションです。以下のブラウザに対応しています。
  * Chrome
  * InternetExplorer
  * Firefox(32bit,64bit)
  * Edge
* スタンドアローンでの実行に加え、SeleniumGridを利用したリモート端末での実行についてもサポートしています。

## テストコードの作成方法
* テストについてはTestSuite(Junit)を利用しているため、Junitによるテストコード作成ルールに則してテストコード作成を行ってください。
* Baseクラスに共通処理を実装していますので、テストクラスについては必ずBase.javaをextendsしてください。(サンプル：SampleTestCase.java)

## テストコードの実行方法
* Junitのテストコード実行の手順と変わりませんが、実行時のパラメータとして以下を環境変数として設定してください。
  * BROWSER_TYPE:実行するブラウザの種類（CHROME | IE | FF32 | FF64 | EDGE）
  * REPLAY_ENV:実行端末（STAND_ALONE | REMOTE）
  * ENV_TYPE:実行環境（任意の文字列を設定、子クラスにてenvTypeで参照可、テスト環境／本番環境のURL切り替え等にご利用ください。）
