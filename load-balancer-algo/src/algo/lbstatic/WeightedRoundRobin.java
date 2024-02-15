package algo.lbstatic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class WeightedRoundRobinLoadBalancer {
	
	private List<Server> servers;
	private int[] cumulativeWeights;
	private int totalWeight;
	private int currentIndex;
	private Random random;
	
	public WeightedRoundRobinLoadBalancer(List<Server> servers) {
		this.servers = new ArrayList<Server>(servers);
		this.currentIndex = 0;
		this.totalWeight = calculateTotalWeight(servers);
		this.cumulativeWeights = calculateCumulativeWeights(servers);;
		this.random = new Random();
		
	}
	
	private int[] calculateCumulativeWeights(List<Server> servers) {
		int[] cumulativeWeights = new int[servers.size()];
        cumulativeWeights[0] = servers.get(0).getWeight();
        for (int i = 1; i < servers.size(); i++) {
            cumulativeWeights[i] = cumulativeWeights[i - 1] + servers.get(i).getWeight();
        }
        return cumulativeWeights;
	}

	private int calculateTotalWeight(List<Server> servers) {
		int totalWeight = 0;
		for(Server server: servers) {
			totalWeight += server.getWeight();
		}
		return totalWeight;
	}
	
	public Server getNextServer() {
        int randomValue = random.nextInt(totalWeight);
        for (int i = 0; i < cumulativeWeights.length; i++) {
            if (randomValue < cumulativeWeights[i]) {
                currentIndex = i;
                break;
            }
        }
        return servers.get(currentIndex);
    }

	// Inner class representing a server with a weight
    static class Server {
    	private String name;
        private int weight;
 
        public Server(String name, int weight) {
            this.name = name;
            this.weight = weight;
        }
 
        public String getName() {
            return name;
        }
 
        public int getWeight() {
            return weight;
        }
    }
}

public class WeightedRoundRobin {

	public static void main(String[] args) {
		 // Sample list of servers with weights
        List<WeightedRoundRobinLoadBalancer.Server> serverList = new ArrayList<>();
        serverList.add(new WeightedRoundRobinLoadBalancer.Server("Server1", 3));
        serverList.add(new WeightedRoundRobinLoadBalancer.Server("Server2", 2));
        serverList.add(new WeightedRoundRobinLoadBalancer.Server("Server3", 1));
 
        // Create a weighted round-robin load balancer with the server list
        WeightedRoundRobinLoadBalancer balancer = new WeightedRoundRobinLoadBalancer(serverList);
 
        // Simulate requests to the load balancer
        for (int i = 0; i < 60; i++) {
            WeightedRoundRobinLoadBalancer.Server nextServer = balancer.getNextServer();
            System.out.println("Request " + (i + 1) + ": Routed to " + nextServer.getName());
        }
	}

}
