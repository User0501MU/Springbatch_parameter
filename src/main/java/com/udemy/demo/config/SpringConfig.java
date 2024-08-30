/*JobLauncher
 * jobRepository
 * Platform Transaction Manager
 * コンフィグクラスを作る（設定のこと）
  */

package com.udemy.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.job.builder.JobBuilder;//ジョブビルダーのインポート文
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.udemy.demo.validator.SampleJobParametersValidator;

import org.springframework.batch.core.step.builder.StepBuilder;//ステップビルダーのインポート文

@Configuration
public class SpringConfig {

	// クラス変数の定義
	private final JobLauncher jobLauncher;
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	@Autowired // Beanを自動的に注入するためのアノテーションです
	@Qualifier("HelloTasklet1") // 同じタイプのBeanが複数存在する場合に、特定のBeanを選んで注入するためのアノテーション
	// ※@Autowired と一緒に使い、どのBeanを注入するかを指定します。@Qualifier の値には、@Component
	// で指定したBeanの名前を指定します。)
	private Tasklet helloTasklet1;

	//tasklet2追加
	@Autowired
	@Qualifier("HelloTasklet2")
	private Tasklet helloTasklet2;

	// 右クリック＞ソース＞フィールドを使用してコンストラクタ生成
	public SpringConfig(JobLauncher jobLauncher, JobRepository jobRepository,
			PlatformTransactionManager transactionManager) {
		// super();不要
		this.jobLauncher = jobLauncher;
		// Spring Batchジョブを起動するためのインターフェース
		this.jobRepository = jobRepository;
		// JobRepository
		// は、ジョブとステップのメタデータ（状態、履歴など）を永続化するためのリポジトリです。ジョブの実行状態や実行結果、ステップの状態などをデータベースに保存します。
		this.transactionManager = transactionManager;
		// TransactionManager は、トランザクションの管理を行うコンポーネントです。Spring
		// Batchでは、トランザクション管理はバッチ処理の整合性を保つために重要です。
	}



	//設定クラスにvalidatorを追加、Jobにも追加
	@Bean
	public JobParametersValidator jobParametersValidator() {
		return new SampleJobParametersValidator();
	}

	// 作業のステップを作る(実行する一つの作業のこと)、メソッドを1つ定義すると完成
	@Bean
	public Step helloTaskletStep1() {
		//StepBuilder: ステップを作るための「設計図」を作っているんだ。
		//JobRepositoryはバッチジョブの状態を管理するために使用(ステップの状態やトランザクション管理)
		return new StepBuilder("helloTasklet1Step", jobRepository)
				//Taskletとは？「ファイルを読み込む」とか「データベースに情報を追加する」とか、そういった具体的な作業を指定
				//helloTasklet1 は、具体的な作業の内容を定義したもの。例えば、「こんにちは、世界！」というメッセージを表示するプログラム
				//transactionManager は「トランザクションマネージャー」と呼ばれるもので、データの変更がうまくいったかどうかを管理する役割
				.tasklet(helloTasklet1, transactionManager)
				.build();
			}

	@Bean
	public Step helloTaskletStep2() {
		return new StepBuilder("helloTasklet2Step",jobRepository)
				.tasklet(helloTasklet2,transactionManager)
				.build();
	}

	//作業をまとめてジョブ（仕事）を作る(複数のステップをまとめたもの)ここでは1つのステップ
	@Bean
	public Job helloJob() {
		return new JobBuilder("helloJob",jobRepository)
				.incrementer(new RunIdIncrementer())
				.start(helloTaskletStep1())
				.next(helloTaskletStep2())//追加
				.validator(jobParametersValidator())//追加
				.build();

	}
}
