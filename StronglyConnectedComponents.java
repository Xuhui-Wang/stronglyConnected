package ucsdm3hw2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class StronglyConnected {
    private static int numberOfStronglyConnectedComponents(ArrayList<Integer>[] adj) {
//        ArrayList<Integer>[] adj_ = (ArrayList<Integer>[])new ArrayList[adj.length];    // create the reverse graph;
        StackOfInts stack0 = new StackOfInts(adj.length);           // store all the source vertices;



        int[] numOfScc = new int[adj.length];       //indicate this vertex is in which SCC;
        int[] inDegree = new int[adj.length];
        for (int i = 0; i < adj.length; i++)
            for (int j : adj[i])
                inDegree[j]++;
        for (int i = 0; i < adj.length; i++)
        {
            if (inDegree[i] == 0)
                stack0.push(i);
        }      
//        System.out.println("size : " + stack0.size());        
        int sccs = 0;        // TOTAL number of SCCs;
        StackOfInts stack = new StackOfInts(adj.length);    
        Queue q = new Queue();
        outer: while (!stack0.isEmpty())
        {
            StackOfInts stack1 = new StackOfInts(adj.length);        // storing those vertices visited;
            int v = stack0.pop();
//            StackOfInts stack1 = new StackOfInts(adj.length);
            q.enqueue(v);
            stack1.push(v);
            boolean[] visited = new boolean[adj.length];      // indicate whether this vertex could be reached from v;
            visited[v] = true;
            loop: while (!q.isEmpty())            //Depth first search;
            {
                int o = q.dequeue();
                for (int j : adj[o])
                {
                    if (numOfScc[j] == 0 && visited[j] == false)            // Not in some loops and not visited before;
                    {
                        visited[j] = true;
                        q.enqueue(j);
                        stack1.push(j);
//                        continue loop;
                    }
                }
//                stack.pop();
//                System.out.println("top vertex: " + stack1.top());
            }
            while (!stack1.isEmpty())
            {
                int sink = stack1.pop();
                System.out.println("sink : " + sink);  // for test;
                if (numOfScc[sink] != 0)            // this vertex has been includes in some loops
                    continue;
                numOfScc[sink] = ++sccs;
                stack.push(sink);
                l: while (!stack.isEmpty())
                {
                    int o = stack.top();
                    for (int j : adj[o])                    
                        if (numOfScc[j] == 0 && visited[j] == true)            // Not in some loops and not visited before;
                        {
                            numOfScc[j] = sccs;
                            stack.push(j);
                            continue l;
                        }   
                    stack.pop();
                }
//                System.out.println("stack1 size : " + stack1.size());                
            }
//            while (!visited.isEmpty())
//            {
//                int o = visited.pop();
//                if (reach(adj, o, v) == 0)
//                    numOfScc[o] = 0;
//            }
//            for (int i = 0; i < numOfScc.length; i++)
//                System.out.print("scc: " + numOfScc[i] + "; ");        //for _test;
//            System.out.println("  ");
        }

            
        return sccs;
    }

    private static class StackOfInts
    {
        private int[] a;
        private int N;
        public StackOfInts(int cap) 
        {
            a = new int[cap];
            N = 0;
        }
        public boolean isEmpty() { return N == 0;}
        public int size() { return N; }
        public void push(int item) { a[N++] = item; }
        public int pop() { return a[--N]; }
        public int top() { 
                return a[N - 1];      //No consideration of null exception(have ruled it out in "reach" function;
        }
    }

    private static class Queue {
        private int first;
        private int last;
        private int N;
        private int[] a;
        public Queue() {
            a = new int[2];
            N = 0;
            first = 0;
            last = 0;
        }
//        public Queue(int size) {
//            a = new int[size];
//            N = 0;
//            first = 0;
//            last = 0;
//        }
        public int size() {
            return N;
        }
        private void resize(int max) {
            if (max < N) return;
            int[] tmp = new int[max];
            for (int i = 0; i < N; i++)
                tmp[i] = a[(first + i) % a.length];
            a = tmp;
            first = 0;
            last = N;
        }
        public boolean isEmpty() {
            return N == 0;
        }
        public void enqueue(int i) {
            if (N == a.length) resize(2 * a.length);
            a[last++] = i;
            if (last == a.length) last = 0;
            N++;
        }
        public int dequeue() {
            if (isEmpty()) throw new NoSuchElementException("Queue underflor");
            int item = a[first];
            a[first] = 0;
            N--;
            first++;
            if (first == a.length) first = 0;
            if (N > 0 && N == a.length / 4) resize(a.length / 2);
            return item;
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
        }
        System.out.println(numberOfStronglyConnectedComponents(adj));
    }
}
