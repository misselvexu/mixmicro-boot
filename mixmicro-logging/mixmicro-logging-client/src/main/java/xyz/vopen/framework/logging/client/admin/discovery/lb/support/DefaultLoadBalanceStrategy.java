package xyz.vopen.framework.logging.client.admin.discovery.lb.support;

import xyz.vopen.framework.logging.client.MinBoxLoggingException;
import xyz.vopen.framework.logging.client.admin.discovery.lb.LoadBalanceNode;
import xyz.vopen.framework.logging.client.admin.discovery.lb.LoadBalanceStrategy;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * The {@link LoadBalanceStrategy} default support
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @see LoadBalanceNode
 */
public abstract class DefaultLoadBalanceStrategy implements LoadBalanceStrategy {

  /**
   * Initialize the list of transformation nodes Convert admin address to {@link LoadBalanceNode}
   *
   * @param adminAddress logging admin address array
   * @return {@link LoadBalanceNode} collection
   */
  protected List<LoadBalanceNode> initNodeList(String[] adminAddress) {
    if (ObjectUtils.isEmpty(adminAddress)) {
      throw new MinBoxLoggingException("Admin address not configured.");
    }
    List<LoadBalanceNode> nodes = new LinkedList();
    Arrays.stream(adminAddress).forEach(address -> nodes.add(new LoadBalanceNode(address)));
    return nodes;
  }
}
