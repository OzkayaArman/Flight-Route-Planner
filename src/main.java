
/*
 * This class contains some examples of required inputs and outputs
 * run with 
 * java P3main <Algo> <N> <ds:as> <dg:ag>
 * we assume <N> <ds:as> <dg:ag> are valid parameters, no need to check
 */

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Comparator;

public class main {

	public static void main(String[] args) {

		if (args.length < 4) {
			System.out.println("usage: java P3main <DFS|BFS|AStar|BestF|SMAStar|...> <N> <ds:as> <dg:ag> [<param>]");
			System.exit(1);
		}
		// Print initial information --- please do not delete
		System.out.println("World: Oedipus " + args[1]);
		System.out.println("Departure airport -- Start: " + args[2]);
		System.out.println("Destination airport -- Goal: " + args[3]);
		System.out.println("Search algorithm: " + args[0]);
		System.out.println();
		// end initial information
		main object = new main();
		// run your search algorithm
		object.runSearch(args[0], Integer.parseInt(args[1]), args[2], args[3]);

	}

	private void runSearch(String algo, int size, String start, String goal) {
		switch (algo) {
		case "BFS": { // run BFS

			// Initialize the frontier as a queue
			Queue frontier = new Queue();
			// Keep track of explored states to avoid loops
			LinkedHashSet<PolarState> explored = new LinkedHashSet<PolarState>();

			// Parse the origin airport information
			String[] initialState = start.split(":");
			String distanceS = initialState[0];
			String degreeS = initialState[1];

			// Parse the destination airport information
			String[] goalState = goal.split(":");
			String distanceG = goalState[0];
			String degreeG = goalState[1];

			// Don't allow worlds with a size less than 1
			if (size < 1) {
				System.out.println("Size has to be at least 1");
				System.exit(1);
			}

			// Initilaize the start state with distance, degree, world size,
			PolarState startState = new PolarState(Integer.parseInt(distanceS), Integer.parseInt(degreeS), size);
			// Initilaize the end state with distance, degree, world size,
			PolarState endState = new PolarState(Integer.parseInt(distanceG), Integer.parseInt(degreeG), size);

			// Initilaize the first node and put it into the frontier
			Node initialNode = makeNode(null, startState);
			frontier.enqueue(initialNode);

			// Input Checking
			if (startState.getDistance() == 0) {
				System.out.println("Starting coordinate can not be the pole");
				System.exit(1);
			}

			// Search Loop
			while (true) {
				// Check whether frontier is empty
				if (frontier.isEmpty()) {
					System.out.println("fail");
					System.out.println(explored.size());
					System.exit(1);
				}

				// Print the frontier before polling it
				frontier.printQueue();

				// If frontier isn't empty pop the queue
				Node front = frontier.dequeue();
				explored.add(front.getState());
				// Check whether goal state is reached
				if (testGoal(front.getState(), endState)) {
					printPath(front);
					System.out.println(explored.size());
					break;
				} else {
					ArrayList<Node> nodesNew = expand(front);
					sortTieBreak(nodesNew);
					for (int i = 0; i < nodesNew.size(); i++) {
						// Add node only if state isn't in frontier or in explored
						if (!explored.contains(nodesNew.get(i).getState())
								&& !frontier.contains(nodesNew.get(i).getState())) {
							frontier.enqueue(nodesNew.get(i));

						}

					}

				}
			}

			break;
		}

		case "DFS": { // run DFS
			// Initialize the frontier
			Stack frontier = new Stack();
			// Keep track of explored states to avoid loops
			LinkedHashSet<PolarState> explored = new LinkedHashSet<PolarState>();

			// Parse the origin airport information
			String[] initialState = start.split(":");
			String distanceS = initialState[0];
			String degreeS = initialState[1];

			// Parse the destination airport information
			String[] goalState = goal.split(":");
			String distanceG = goalState[0];
			String degreeG = goalState[1];

			if (size < 1) {
				System.out.println("Size has to be at least 1");
				System.exit(1);
			}

			// Initilaize the start state with distance, degree, world size, and flag to
			// indicate this is start state
			PolarState startState = new PolarState(Integer.parseInt(distanceS), Integer.parseInt(degreeS), size);
			PolarState endState = new PolarState(Integer.parseInt(distanceG), Integer.parseInt(degreeG), size);
			Node initialNode = makeNode(null, startState);
			frontier.push(initialNode);

			if (startState.getDistance() == 0) {
				System.out.println("Starting coordinate can not be the pole");
				System.exit(1);
			}

			while (true) {

				// Check whether frontier is empty
				if (frontier.isEmpty()) {
					System.out.println("fail");
					System.out.println(explored.size());
					System.exit(1);
				}

				frontier.printStack();

				// If frontier isn't empty pop the queue
				Node front = frontier.pop();
				explored.add(front.getState());
				// Check whether goal state is reached
				if (testGoal(front.getState(), endState)) {
					printPath(front);
					System.out.println(explored.size());
					break;
				} else {
					ArrayList<Node> nodesNew = expand(front);
					sortTieBreak(nodesNew);
					for (int i = nodesNew.size() - 1; i >= 0; i--) {
						// Add node only if state isn't in frontier or in explored
						if (!explored.contains(nodesNew.get(i).getState())
								&& !frontier.contains(nodesNew.get(i).getState())) {
							frontier.push(nodesNew.get(i));
						}

					}

				}
			}

			break;
		}
		case "BestF": { // run BestF
			// Initialize the frontier
			Queue frontier = new Queue();
			// Keep track of explored states to avoid loops
			LinkedHashSet<PolarState> explored = new LinkedHashSet<PolarState>();

			// Parse the origin airport information
			String[] initialState = start.split(":");
			String distanceS = initialState[0];
			String degreeS = initialState[1];

			// Parse the destination airport information
			String[] goalState = goal.split(":");
			String distanceG = goalState[0];
			String degreeG = goalState[1];

			if (size < 1) {
				System.out.println("Size has to be at least 1");
				System.exit(1);
			}

			// Initilaize the start state with distance, degree, world size, and flag to
			// indicate this is start state
			PolarState startState = new PolarState(Integer.parseInt(distanceS), Integer.parseInt(degreeS), size);
			PolarState endState = new PolarState(Integer.parseInt(distanceG), Integer.parseInt(degreeG), size);
			Node initialNode = makeNode(null, startState);
			initialNode.setEuclidianCost(calculateGoalCostEuclidian(initialNode.getState(), endState));
			frontier.enqueue(initialNode);

			if (startState.getDistance() == 0) {
				System.out.println("Starting coordinate can not be the pole");
				System.exit(1);
			}

			while (true) {

				// Check whether frontier is empty
				if (frontier.isEmpty()) {
					System.out.println("fail");
					System.out.println(explored.size());
					System.exit(1);
				}

				frontier.printQueue(true);

				// If frontier isn't empty pop the queue
				Node front = frontier.dequeue();
				explored.add(front.getState());
				// Check whether goal state is reached
				if (testGoal(front.getState(), endState)) {
					printPath(front);
					System.out.println(explored.size());
					break;
				} else {
					ArrayList<Node> nodesNew = expand(front, endState);
					for (int i = 0; i < nodesNew.size(); i++) {
						// Add node only if state isn't in frontier or in explored
						if (!explored.contains(nodesNew.get(i).getState())
								&& !frontier.contains(nodesNew.get(i).getState())) {
							frontier.enqueue(nodesNew.get(i));
						}

					}
					frontier.sortBestFirst();
				}
			}
			break;
		}
		case "AStar": { // run AStar
			// Initialize the frontier
			Queue frontier = new Queue();
			// Keep track of explored states to avoid loops
			LinkedHashSet<PolarState> explored = new LinkedHashSet<PolarState>();

			// Parse the origin airport information
			String[] initialState = start.split(":");
			String distanceS = initialState[0];
			String degreeS = initialState[1];

			// Parse the destination airport information
			String[] goalState = goal.split(":");
			String distanceG = goalState[0];
			String degreeG = goalState[1];

			if (size < 1) {
				System.out.println("Size has to be at least 1");
				System.exit(1);
			}

			// Initilaize the start state with distance, degree, world size, and flag to
			// indicate this is start state
			PolarState startState = new PolarState(Integer.parseInt(distanceS), Integer.parseInt(degreeS), size);
			PolarState endState = new PolarState(Integer.parseInt(distanceG), Integer.parseInt(degreeG), size);
			Node initialNode = makeNode(null, startState);
			initialNode.setEuclidianCost(calculateGoalCostEuclidian(initialNode.getState(), endState));
			initialNode.setOverallCost(initialNode.getEuclidianCost());
			frontier.enqueue(initialNode);

			if (startState.getDistance() == 0) {
				System.out.println("Starting coordinate can not be the pole");
				System.exit(1);
			}

			while (true) {

				// Check whether frontier is empty
				if (frontier.isEmpty()) {
					System.out.println("Fail");
					System.out.println(explored.size());
					System.exit(1);
				}

				frontier.printQueue(true);

				// If frontier isn't empty pop the queue
				Node front = frontier.dequeue();
				explored.add(front.getState());
				// Check whether goal state is reached
				if (testGoal(front.getState(), endState)) {
					printPath(front);
					System.out.println(explored.size());
					break;
				} else {
					ArrayList<Node> nodesNew = expand(front, endState);
					for (int i = 0; i < nodesNew.size(); i++) {
						// Add node only if state isn't explored
						if (!explored.contains(nodesNew.get(i).getState())) {
							// If node is in the frontier, compare the overall cost of the new node against
							// the node already in the frontier and replace it if lower
							Node frontierQueryNode = frontier.containsA(nodesNew.get(i).getState());
							if (frontierQueryNode != null) {
								if (frontierQueryNode.getOverallCost() > nodesNew.get(i).getOverallCost()) {
									frontier.remove(frontierQueryNode);
									frontier.enqueue(nodesNew.get(i));
									continue;
								} else {
									// Do not enqueue the new node if overall cost of it is higher than the node
									// already in the frontier that has the same state
									continue;
								}
							}
							// Put the node into frontier if node isn't explored and if it isn't in the
							// frontier already
							frontier.enqueue(nodesNew.get(i));
						}

					}
					frontier.sortAStar();
				}
			}
			break;
		}

		}

	}

	public boolean ancestorCheck(Node nodeToBeChecked, Node ancestorReference) {
		Node nodeBeingChecked = nodeToBeChecked;
		while (nodeBeingChecked.getPredecessorPointer() != null) {
			if (nodeBeingChecked.equals(ancestorReference)) {
				return true;
			} else {
				nodeBeingChecked = nodeBeingChecked.getPredecessorPointer();
			}
		}
		return false;
	}

	public Node makeNode(Node parentNode, PolarState currentState) {
		Node newNode = new Node();
		newNode.setState(currentState);
		newNode.setPredecessorPointer(parentNode);
		if (parentNode != null) {
			newNode.setDepth(parentNode.getDepth() + 1);
			newNode.setCost(parentNode.getCost() + calculateCost(parentNode.getState(), currentState));
		}
		return newNode;
	}

	// Expand for uninformed search
	public ArrayList<Node> expand(Node currentNode) {
		ArrayList<Node> nodesNew = new ArrayList<Node>();
		ArrayList<PolarState> statesNew = successorFunction(currentNode.getState());

		for (int i = 0; i < statesNew.size(); i++) {
			Node newNode = makeNode(currentNode, statesNew.get(i));
			nodesNew.add(newNode);
		}
		return nodesNew;
	}

	// Expand for informed search
	public ArrayList<Node> expand(Node currentNode, PolarState goalStateIn) {
		ArrayList<Node> nodesNew = new ArrayList<Node>();
		ArrayList<PolarState> statesNew = successorFunction(currentNode.getState());

		for (int i = 0; i < statesNew.size(); i++) {
			Node newNode = makeNode(currentNode, statesNew.get(i));
			// Set the euclidian cost approximation to goal state of the new node's state
			newNode.setEuclidianCost(calculateGoalCostEuclidian(newNode.getState(), goalStateIn));
			newNode.setOverallCost(newNode.getEuclidianCost() + newNode.getCost());
			nodesNew.add(newNode);
		}
		return nodesNew;
	}

	public ArrayList<Node> expandSM(Node currentNode, PolarState goalStateIn, int memory) {
		ArrayList<Node> nodesNew = new ArrayList<Node>();
		ArrayList<PolarState> statesNew = successorFunction(currentNode.getState());
		boolean flag = false;

		if (currentNode.isForgottenEmpty()) {
			for (int i = 0; i < statesNew.size(); i++) {
				Node newNode = makeNode(currentNode, statesNew.get(i));
				// Set the euclidian cost approximation to goal state of the new node's state
				newNode.setEuclidianCost(calculateGoalCostEuclidian(newNode.getState(), goalStateIn));
				newNode.setOverallCost(newNode.getEuclidianCost() + newNode.getCost());
				nodesNew.add(newNode);
			}
		} else {
			nodesNew = currentNode.getForgotten();
			flag = true;
		}

		for (int index = 0; index < nodesNew.size(); index++) {
			if (flag) {
				currentNode.deleteForgotten(0);
			} else {
				if (!testGoal(nodesNew.get(index).getState(), goalStateIn)
						&& nodesNew.get(index).getDepth() == memory) {
					nodesNew.get(index).setOverallCost(10000);
				}
			}
			nodesNew.get(index).leaf = true;
			nodesNew.get(index).getPredecessorPointer().leaf = false;

		}
		return nodesNew;
	}

	public ArrayList<PolarState> successorFunction(PolarState stateIn) {
		ArrayList<PolarState> createdStates = new ArrayList<PolarState>();
		int distanceIn = stateIn.getDistance();
		int angleIn = stateIn.getAngle();
		int sizeIn = stateIn.getSize();

		if ((distanceIn != 1 && distanceIn < sizeIn - 1)) {
			int newAngle3 = (angleIn - 45 + 360) % 360; // Calculate the new angle for West
			int newAngle4 = (angleIn + 45) % 360; // Calculate the new angle for East

			PolarState newState = new PolarState((distanceIn == 0) ? 1 : (distanceIn - 1), angleIn, sizeIn);// North
			PolarState newState2 = new PolarState(distanceIn + 1, angleIn, sizeIn); // South
			PolarState newState3 = new PolarState(distanceIn, newAngle3, sizeIn); // West
			PolarState newState4 = new PolarState(distanceIn, newAngle4, sizeIn); // East

			createdStates.add(newState);
			createdStates.add(newState2);
			createdStates.add(newState3);
			createdStates.add(newState4);
		}

		// Handle the case where we can't go south anymore
		if (distanceIn != 1 && distanceIn == sizeIn - 1) {
			int newAngle3 = (angleIn - 45 + 360) % 360; // Calculate the new angle for West
			int newAngle4 = (angleIn + 45) % 360; // Calculate the new angle for East

			PolarState newState = new PolarState(distanceIn - 1, angleIn, sizeIn); // North
			PolarState newState3 = new PolarState(distanceIn, newAngle3, sizeIn); // West
			PolarState newState4 = new PolarState(distanceIn, newAngle4, sizeIn); // East
			createdStates.add(newState);
			createdStates.add(newState3);
			createdStates.add(newState4);
		}
		// Handle the case where we can't fly over the pole
		if (distanceIn == 1 && distanceIn < sizeIn - 1) {
			int newAngle3 = (angleIn - 45 + 360) % 360; // Calculate the new angle for West
			int newAngle4 = (angleIn + 45) % 360; // Calculate the new angle for East

			PolarState newState2 = new PolarState(distanceIn + 1, angleIn, sizeIn); // South
			PolarState newState3 = new PolarState(distanceIn, newAngle3, sizeIn); // West
			PolarState newState4 = new PolarState(distanceIn, newAngle4, sizeIn); // East
			createdStates.add(newState2);
			createdStates.add(newState3);
			createdStates.add(newState4);
		}

		// Handle the case where we can't fly over the pole and can't fly south
		if (distanceIn == 1 && distanceIn == sizeIn - 1) {
			int newAngle3 = (angleIn - 45 + 360) % 360; // Calculate the new angle for West
			int newAngle4 = (angleIn + 45) % 360; // Calculate the new angle for East

			PolarState newState3 = new PolarState(distanceIn, newAngle3, sizeIn); // West
			PolarState newState4 = new PolarState(distanceIn, newAngle4, sizeIn); // East

			createdStates.add(newState3);
			createdStates.add(newState4);
		}
		return createdStates;
	}

	public double calculateGoalCostEuclidian(PolarState oldState, PolarState newState) {
		double cost;
		int distanceOld = oldState.getDistance();
		int distanceNew = newState.getDistance();
		int angleOld = oldState.getAngle();
		int angleNew = newState.getAngle();
		double angleO = Math.toRadians(angleOld);
		double angleN = Math.toRadians(angleNew);

		cost = Math.sqrt(distanceOld * distanceOld + distanceNew * distanceNew
				- (2 * distanceOld * distanceNew * Math.cos(angleN - angleO)));
		return cost;
	}

	public double calculateCost(PolarState oldState, PolarState newState) {
		int distanceOld = oldState.getDistance();
		int distanceNew = newState.getDistance();

		if (Math.abs(distanceOld - distanceNew) == 1) {
			return 1;
		}
		return (2 * Math.PI * distanceNew) / 8;

	}

	public boolean testGoal(PolarState stateIn, PolarState stateGoal) {
		return (stateIn.getDistance() == stateGoal.getDistance()) && (stateIn.getAngle() == stateGoal.getAngle());
	}

	private void printPath(Node goalNode) {
		LinkedList<Node> path = new LinkedList<>();
		Node current = goalNode;
		// Follow the predecessor pointer to construct the path
		while (current != null) {
			path.addFirst(current);
			current = current.getPredecessorPointer();
		}
		// Print the constructed path
		for (Node node : path) {
			System.out.print("(" + node.getState().getDistance() + ":" + node.getState().getAngle() + ")");
		}
		System.out.println();
		System.out.printf("%.3f%n", goalNode.getCost());
	}

	public void sortTieBreak(ArrayList<Node> listIn) {
		listIn.sort(Comparator.comparingInt((Node node) -> node.getState().getDistance())
				.thenComparingInt(node -> node.getState().getAngle()));
	}

}
