/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-11-19 下午1:25:04
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.common;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.validation.DataValidation;
import com.yutong.clw.ygbclient.common.validation.VerifyResult;

/**
 * @author zhangzhia 2013-11-19 下午1:25:04
 */
public class DataValidationUtil {

	/**
	 * 根据验证规则验证指定字符串。
	 * 
	 * @author zhangyongn 2013-11-20 下午1:40:33
	 * 
	 * @param ruleStrings
	 *            规则字符串，具体格式参考相关文档
	 * @param str
	 *            待验证字符串
	 * @return 如果通过，则返回空字符串 “”；如果失败，则返回具体的错误字符串。
	 */
	public static String validate(String ruleStrings, String str) {
		boolean singleflag = true;
		VerifyResult results = DataValidation.validate(ruleStrings, str,
				singleflag);

		if (results.isPass) {
			Logger.i(DataValidationUtil.class, "数据有效性验证通过");
			return "";
		} else {
			StringBuilder builder = new StringBuilder();
			if (singleflag) {
				builder.append(results.faildesc);
			} else {
				int count = 0;
				for (VerifyResult item : results.verifyResults) {
					if (count != 0)
						builder.append("\n");
					builder.append(item.faildesc);
					count++;
				}
			}
			Logger.w(DataValidationUtil.class,
					"数据有效性验证不通过，原因：" + builder.toString());

			return builder.toString();

		}
	}
}
