package example.billingjob;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

public class BillingDataProcessor implements ItemProcessor<BillingData, ReportingData> {

	@Value("${spring.cellular.pricing.data:0.01}")
	private float dataPricing;

	@Value("${spring.cellular.pricing.call:0.5}")
	private float callPricing;

	@Value("${spring.cellular.pricing.sms:0.1}")
	private float smsPricing;

	@Value("${spring.cellular.spending.threshold:150}")
	private float spendingThreshold;

	@Override
	public ReportingData process(BillingData item) {
		double billingTotal = item.dataUsage() * dataPricing + item.callDuration() * callPricing + item.smsCount() * smsPricing;
		if (billingTotal < spendingThreshold) {
			return null;
		}
		return new ReportingData(item, billingTotal);
	}

}
