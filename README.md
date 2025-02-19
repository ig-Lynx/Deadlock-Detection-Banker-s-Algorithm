# 🔄 Deadlock Detection & Banker's Algorithm

## 📌 Overview
This project implements deadlock detection and the Banker's Algorithm in Java. It provides two different implementations:
1. **Deadlock Detection using an Array-based Approach** 📝
2. **Deadlock Detection using a Matrix-based Approach** 🧮

The system manages processes and resources, checks for safe execution sequences, detects deadlocks, and ensures safe allocation of resources to prevent system failures.

## 🌟 Features
- 🏗️ Implements the **Banker's Algorithm** for deadlock avoidance.
- 🚨 Detects deadlocks in a system using **Wait-for Graph Analysis**.
- 📊 Two different approaches:
  - **Array-based Approach** ➝ Works with a simplified model using single resource types.
  - **Matrix-based Approach** ➝ Supports multiple resources per process.
- ✅ Ensures safe state before granting resource requests.
- 🔄 Allows process dependencies and resource allocation management.

## 🛠️ Technologies Used
### 1. ☕ Java
- The core programming language for implementation.
- Uses **data structures like arrays, matrices, and lists** to manage resources.
- Supports **object-oriented principles (encapsulation, abstraction, and modular design)**.

### 2. 🧮 Banker's Algorithm
- Prevents deadlocks by ensuring a **safe sequence of execution**.
- Checks whether granting a resource request keeps the system in a safe state.

### 3. 🔗 Wait-for Graph (WFG)
- Detects cycles in process dependencies.
- Implements **Depth-First Search (DFS) algorithm** to identify deadlocks.

### 4. 🔀 Resource Allocation Management
- Uses **allocation matrices, maximum requirement matrices, and need matrices** to track resource distribution.
- Ensures processes do not exceed declared maximum resource requests.

## 📂 File Structure
```
├── DeadlockBankerArray.java   # Array-based deadlock detection & Banker's Algorithm
├── DeadlockBankerMatrix.java  # Matrix-based deadlock detection & Banker's Algorithm
└── README.md                  # Documentation
```

## 🚀 How It Works
### 1️⃣ **Deadlock Detection (Array-based Approach)**
- Uses **single resource type** per process.
- Maintains **allocation, max, and available arrays**.
- Detects unsafe states and resolves deadlocks.

### 2️⃣ **Deadlock Detection (Matrix-based Approach)**
- Supports **multiple resources per process**.
- Uses **allocation, max, need, and available matrices**.
- Checks for safe execution sequence before granting resource requests.

## 🔧 Installation & Setup
1. **Compile the Java programs**
   ```bash
   javac DeadlockBankerArray.java
   javac DeadlockBankerMatrix.java
   ```
2. **Run the desired implementation**
   ```bash
   java DeadlockBankerArray
   java DeadlockBankerMatrix
   ```

## 🎯 Usage
1. 🔢 Enter the number of processes and resources.
2. 📊 Input **allocation, max, and available** resources.
3. 🔍 Choose to:
   - Detect deadlocks 🚨
   - Request resources 🔄
   - Check if the system is in a safe state ✅
4. 🛑 If deadlock is detected, the program will notify and roll back changes.
5. 🔄 If a resource request is valid, it will be granted safely.

## 🔮 Future Enhancements
- 🖥️ Implement a **Graphical User Interface (GUI)** for better interaction.
- 📡 Integrate with a **real-time operating system simulator**.
- 🛠️ Optimize the deadlock detection algorithm for large-scale systems.

## 👨‍💻 Contributors
- **Daksh R Laxetti** (dakshrl15@gmail.com)


## 📜 License
This project is licensed under the MIT License.

