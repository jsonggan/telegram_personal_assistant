from contextlib import asynccontextmanager
from fastapi import FastAPI
from fastmcp import FastMCP
from app.core.config import settings

mcp = FastMCP("Telegram Personal Assistant Tools")

@mcp.tool
def get_weather(location: str) -> dict:
    return {
        "location": location,
        "temperature": "22°C",
        "condition": "Sunny",
        "humidity": "65%"
    }

mcp_app = mcp.http_app(path='/mcp')

# Combined lifespan function
@asynccontextmanager
async def combined_lifespan(app: FastAPI):
    # Startup
    print("Starting up the Telegram Personal Assistant...")
    print("MCP server initialized with new OpenAPI parser")
    # Use nested async with to properly manage both lifespans
    async with mcp_app.lifespan(app):
        yield
    # Shutdown
    print("Shutting down the Telegram Personal Assistant...")

# Create FastAPI app with combined lifespan
app = FastAPI(
    description="A personal assistant bot for Telegram with MCP integration",
    lifespan=combined_lifespan
)

# Mount the MCP server
app.mount("/mcp", mcp_app)

@app.get("/")
async def root():
    return {
        "message": "Telegram Personal Assistant API is running!",
        "mcp_endpoint": "/mcp",
        "docs": "/docs",
        "openapi": "/openapi.json"
    }

@app.get("/health")
async def health_check():
    return {"status": "healthy"}

