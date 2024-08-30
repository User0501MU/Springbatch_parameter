package com.udemy.demo.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//taskletを実装したときに@Component（”名称”）とStepScopeが必要
@Component("HelloTasklet1") // Springのコンポーネントスキャン機能を使って、HelloTasklet1
							// クラスをSpringの管理対象のBeanとして登録し、"HelloTasklet1" という名前で利用できるようにする注釈
@StepScope // バッチ処理の中でステップごとに異なる設定やデータを使えるようにする注釈
//インターフェースを実装 lemplemntsでTaskletを実装
public class Tasklet1 implements Tasklet {

	// ジョブに対してパラメータ（引数）を受け取るためのクラス変数を定義し、取得したパラメータを標準出力する
	// Bootダッシュボードから「構成を開く」より引数を指定し、実行結果をコンソールで確認
	@Value("#{jobParameters['param1']}")
	private String param1;

	@Value("#{jobParameters['param2']}")
	private Integer param2;

	//実装されてないメソッドを追加
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		System.out.println("Hello Tasaklet1 !!!");

		// パラメータの標準出力
		//syso＋Ctrl＋space
		System.out.println("Hello Tasklete1 !!!");
		System.out.println("param1="+param1);
		System.out.println("param2="+param2);


		//JobExexutionContextへの値の設定
		ExecutionContext jobContext = contribution.getStepExecution()
				.getJobExecution()
				.getExecutionContext();
		jobContext.put("jobkey1","jobValue1");//キー位置、値の設定

		// nullは完了
		// リピートステータス（Repeatstatus）タスクの実行状態を示します／RepeatStatus.FINISHED:
		// タスクが完了、CONTINUABLE完了していない
		// 再度executeメソッドが呼ばれる
		return RepeatStatus.FINISHED;
	}

}
