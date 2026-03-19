# Conversational AI Support Agents

A conversational support system with two AI agents collaborating within a single conversation. The system dynamically routes user messages to the most appropriate agent depending on the type of request.

## Agents

- **Technical Specialist** — answers technical questions using local documentation with vector-based retrieval (RAG)
- **Billing Specialist** — handles billing requests using tool calling (check plans, billing history, explain refund policy)
- **Orchestrator** — routes each user message to the correct agent based on conversation context

## Prerequisites

- Java 21
- Maven 3.6+
- OpenAI API key

## Setup

### 1. Clone the repository
```bash
git clone <repo-url>
cd <project-name>
```

### 2. Set the OpenAI API key

**Windows:**
```bash
set OPENAI_API_KEY=sk-...
```

**Mac/Linux:**
```bash
export OPENAI_API_KEY=sk-...
```

Or set it permanently via System Environment Variables (recommended).

### 3. Add documentation files

Place your technical documentation files in:
```
src/main/resources/docs/
```

The following files are expected by default:
```
src/main/resources/docs/troubleshooting_integration.txt
src/main/resources/docs/configuration_tips.txt
src/main/resources/docs/api_usage_guidance.txt
```

To use different files, update the list in `Main.java`:
```java
List<String> documents = loader.loadFullDocuments(List.of(
        "docs/troubleshooting_integration.txt",
        "docs/configuration_tips.txt",
        "docs/api_usage_guidance.txt"
));
```

### 4. Build and run the project
```bash
mvn compile exec:java


## Usage

Once the app starts, it will embed the documentation files (this takes a few seconds on first run) and then prompt you for input:

```
Welcome! Type 'exit' to quit.

You: 
```

Type your message and press Enter. The system will route your message to the appropriate agent and display the response.

```
You: How do I configure webhook security?

Technical Agent: To configure webhook security, you need to validate the webhook signature...

You: What is the billing plan for customer_001?

Billing Agent: The current billing plan for customer_001 is the Pro plan at $49/month.

You: exit
Goodbye!
```

## Project Structure

```
src/
└── main/
    ├── java/com/example/
    │   ├── Main.java                        — conversation loop
    │   ├── OpenAiService.java               — OpenAI API calls (chat, tools, embeddings)
    │   ├── Message.java                     — message model
    │   ├── ConversationHistory.java         — shared conversation history
    │   ├── HttpClientService.java           — helper class for managing http requests
    │   ├── CustomerData.java                — in-memory mock customer data
    │   ├── agents/
    │   │   ├── Agent.java                   — base agent class
    │   │   ├── OrchestratorAgent.java       — routes messages to correct agent
    │   │   ├── TechnicalAgent.java          — answers from documentation
    │   │   └── BillingAgent.java            — handles billing with tool calling
    │   ├── documentsManager/
    │   │   ├── DocumentLoader.java          — loads and chunks documentation files
    │   │   ├── DocRetriever.java            — embeds chunks and retrieves relevant ones
    │   │   └── VectorStore.java             — in-memory vector store with cosine similarity
    │   └── tools/
    │       ├── Tool.java                    — tool interface
    │       ├── ToolExecutor.java            — dispatches tool calls by name
    │       ├── GetPlanTool.java
    │       ├── GetBillingHistoryTool.java
    │       ├── AddBillingRecordTool.java
    │       └── ExplainRefundPolicyTool.java
    └── resources/
        └── docs/
            ├── troubleshooting_integration.txt
            ├── configuration_tips.txt
            └── api_usage_guidance.txt
```

## Available Customer IDs (for testing)

| Customer ID   | Plan                    |
|---------------|-------------------------|
| customer_001  | Pro — $49/month         |
| customer_002  | Basic — $19/month       |
| customer_003  | Enterprise — $199/month |

## Dependencies

- `org.json` — JSON parsing