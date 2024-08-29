package com.udemy.demo.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

//taskletを実装したときに@Component（”名称”）とStepScopeが必要
@Component("HelloTasklet1")//Springのコンポーネントスキャン機能を使って、HelloTasklet1 クラスをSpringの管理対象のBeanとして登録し、"HelloTasklet1" という名前で利用できるようにする注釈
@StepScope//バッチ処理の中でステップごとに異なる設定やデータを使えるようにする注釈
//インターフェースを実装 lemplemntsでTaskletを実装
public class Tasklet1 implements Tasklet {

//実装されてないメソッドを追加
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		System.out.println("Hello Tasaklet1 !!!");

		//nullは完了
		//リピートステータス（Repeatstatus）タスクの実行状態を示します／RepeatStatus.FINISHED: タスクが完了、CONTINUABLE完了していない
		//再度executeメソッドが呼ばれる
		return RepeatStatus.FINISHED;
	}

}
