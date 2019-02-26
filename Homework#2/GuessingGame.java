
import TreePackage.DecisionTreeInterface;
import TreePackage.DecisionTree;
import java.util.Iterator;
import java.util.Scanner;

public class GuessingGame {

    private DecisionTree<String> tree;
    private DecisionTree<String> current;

    public GuessingGame() {
        /* Tree Diagram:
                 Mammal?
                 /   \
               yes   no
               /      \
             pet?    bird?
            / \       /  \
         cat farm? penguin reptile?
             /  \          /   \
          goat elephant   lizard squid
         */
        DecisionTree<String> cat = new DecisionTree<>("cat", null, null);
        DecisionTree<String> Goat = new DecisionTree<>("goat", null, null);
        DecisionTree<String> Elephant = new DecisionTree<>("elephant", null, null);
        DecisionTree<String> FarmAnimal = new DecisionTree<>("farm animal", Goat, Elephant);
        DecisionTree<String> Pet = new DecisionTree<>("pet", cat, FarmAnimal);
        DecisionTree<String> Squid = new DecisionTree<>("lizard", null, null);
        DecisionTree<String> Spider = new DecisionTree<>("squid", null, null);
        DecisionTree<String> Ocean = new DecisionTree<>("reptile", Squid, Spider);
        DecisionTree<String> Penguin = new DecisionTree<>("penguin", null, null);
        DecisionTree<String> Bird = new DecisionTree<>("bird", Penguin, Ocean);
        DecisionTree<String> Mammal = new DecisionTree("mammal", Pet, Bird);
        tree = Mammal;
    }

    public void playGame() {
        System.out.println("Hello. Welcome to the Guessing Game. Please enter (yes) for yes, and (no) for no.");
        play();
        print();
        tree.resetCurrentNode();
    }

    public void play() {
        current = tree;
        while (!current.isAnswer()) {
            if (check("Is it a " + current.getCurrentData() + ": ")) {
                current.advanceToYes();
            } else {
                current.advanceToNo();
            }
        }
        if (!check("Is it a " + current.getCurrentData() + ": ")) {
            learn();
        } else {
            System.out.println("The computer wins.");
        }
    }

    public void learn() {
        String guessAnimal = current.getCurrentData();
        System.out.print("What is it: ");
        Scanner key = new Scanner(System.in);
        String correctAnimal = key.nextLine();
        System.out.print("Please type a yes/no question that will distinguish a " + correctAnimal + " from a " + guessAnimal + ": ");
        String newQuestion = key.nextLine();
        current.setCurrentData(newQuestion);
        System.out.println("As a " + correctAnimal + ", " + newQuestion);
        if (check("Please answer: ")) {
            current.setResponses(guessAnimal, correctAnimal);
        } else {
            current.setResponses(correctAnimal, guessAnimal);
        }
    }

    public void print() {
        if (check("Type (yes) for a post order transversal, or (no) for a level order transversal: ")) {
            Iterator<String> iterator = tree.getPostorderIterator();
            while (iterator.hasNext()) {
                System.out.print(iterator.next() + " ");
            }
        } else {
            Iterator<String> iterator = tree.getLevelOrderIterator();
            while (iterator.hasNext()) {
                System.out.print(iterator.next() + " ");
            }
        }
        System.out.println();
    }

    public boolean check(String question) {
        Scanner key = new Scanner(System.in);
        System.out.print(question);
        String answer = key.nextLine();
        while (!answer.equals("yes") && !answer.equals("no")) {
            System.out.print("Invalid response. Please type (yes) or (no): ");
            answer = key.nextLine();
        }
        return answer.equals("yes");
    }
}
