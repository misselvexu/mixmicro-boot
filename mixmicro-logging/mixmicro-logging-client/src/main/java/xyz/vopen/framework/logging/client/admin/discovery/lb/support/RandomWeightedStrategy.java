package xyz.vopen.framework.logging.client.admin.discovery.lb.support;

import xyz.vopen.framework.logging.client.MinBoxLoggingException;
import xyz.vopen.framework.logging.client.admin.discovery.lb.LoadBalanceNode;
import org.springframework.util.ObjectUtils;
import xyz.vopen.framework.logging.client.admin.discovery.lb.LoadBalanceStrategy;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * The {@link LoadBalanceStrategy} random
 * strategy
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @see DefaultLoadBalanceStrategy
 * @see LoadBalanceStrategy
 */
public class RandomWeightedStrategy extends DefaultLoadBalanceStrategy {
  /** logging admin node list {@link LoadBalanceNode} */
  private TreeMap<Double, LoadBalanceNode> nodes = new TreeMap();

  /**
   * lookup logging admin load-balanced address {@link LoadBalanceNode#getAddress()} Lookup
   * according to random weight admin address get firstKey by {@link SortedMap#tailMap(Object)}
   *
   * @param adminAddress logging admin address array
   * @return Load-balanced addresses
   * @throws MinBoxLoggingException MinBox Logging Exception
   */
  @Override
  public String lookup(String[] adminAddress) throws MinBoxLoggingException {
    List<LoadBalanceNode> loadBalanceNodes = initNodeList(adminAddress);
    loadBalanceNodes.stream()
        .forEach(
            node -> {
              double lastWeight = this.nodes.size() == 0 ? 0 : this.nodes.lastKey().doubleValue();
              this.nodes.put(node.getInitWeight() + lastWeight, node);
            });
    Double randomWeight = this.nodes.lastKey() * Math.random();
    SortedMap<Double, LoadBalanceNode> tailMap = this.nodes.tailMap(randomWeight, false);
    if (ObjectUtils.isEmpty(tailMap)) {
      throw new MinBoxLoggingException("No load balancing node was found");
    }
    return this.nodes.get(tailMap.firstKey()).getAddress();
  }
}
