//validatorとは、入力されたデータが正確であり、指定された形式や条件に従っているかを確認（= バリデーション）する機能
package com.udemy.demo.validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class SampleJobParametersValidator implements JobParametersValidator {
	// 赤線、定義されてないメソッドを追加

	@Override
	public void validate(JobParameters parameters) throws JobParametersInvalidException {
		// パラメーター１の値チェック
		/*DEV（開発環境）: ソフトウェアを作っている場所。
		TEST（テスト環境）: ソフトウェアを試して、問題がないか確認する場所。
		PROD（本番環境）: 実際にユーザーが使う場所。*/
		String param1 = parameters.getString("param1");
		if (!param1.equals("DEV") && !param1.equals("TEST") && !param1.equals("PROD")) {
			throw new JobParametersInvalidException("param1:" + param1 + "DEV/TEST/PRODのいずれかを指定せよ");
		}
			// パラメーター2の値チェック
			String param2 = parameters.getString("param2");
			// 数値であることをチェック
			try {
				Integer.parseInt(param2);
			} catch (Exception e) {
				throw new JobParametersInvalidException("param2=" + param2 + "param2は数値を指定してください");

			}
		}

	}


