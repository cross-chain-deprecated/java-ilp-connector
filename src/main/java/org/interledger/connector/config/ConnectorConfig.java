package org.interledger.connector.config;

import java.math.BigDecimal;
import java.time.Duration;

/**
 * Defines Connector-wide configuration properties that affect the connector as a whole.
 */
public interface ConnectorConfig {

  /**
   * <p>This value allows connectors to determine the window of time required between when an incoming and outgoing
   * transfer should expire. Connectors set this window such that they are confident that they will be able to timely
   * deliver a fulfillment back to the source ledger, even if the outgoing transfer is executed at the last possible
   * moment.</p>
   *
   * <p>This value is appended to the expiration date/time of the corresponding source transfer. So, for example, if a
   * source transfer expires at 08:00:00, and this connector wants to budget 5 seconds for downstream Interledger node
   * operations, then the outoing transfer would be created with an expiration date/time of 08:00:05.</p>
   *
   * <p>Note: This value correlates to the "minimum message window" in the Javascript implementation.</p>
   *
   * <p>Defaults to 5 seconds.</p>
   *
   * @see "https://github.com/interledger/rfcs/blob/master/0018-connector-risk-mitigations/0018-connector-risk-mitigations.md#fulfillment-failure"
   */
  default Duration getTransferExpiryWindow() {
    return Duration.ofSeconds(5);
  }

  /**
   * The FX-spread percentage, in decimal form, added to every quoted rate. By default, this is method return 0.2%, or
   * 0.002.
   *
   * @return A {@link BigDecimal} representing the FX-spread percentage, in decimal-form.
   */
  default BigDecimal getFxSpread() {
    return new BigDecimal("0.002"); // 0.2%
  }

  /**
   * The percentage amount, in decimal form, that a payment may deviate from the quoted amount before this connector
   * will consider the payment to be invalid.
   *
   * Because some ledgers are unable to offer exact quoting (due to liquidity changes that may have occurred between
   * quoting and actual payment), the actual amount of a payment may diverge against this connector (i.e., the fx rate
   * may get worse). This is called "slippage", and is tolerated up to a maximum threshold percentage defined by this
   * configuration value.
   *
   * @return A {@link BigDecimal} representing the slippage percent, in decimal-form.
   */
  default BigDecimal getDefaultSlippagePercent() {
    return new BigDecimal("0.001"); // 0.1%
  }
}
