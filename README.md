# Telegram Personal Assistant

A FastAPI-based personal assistant bot for Telegram.

## Quick Start

```bash
# Install dependencies
make install

# Run development server
make dev
```

## Available Commands

- `make install` - Install dependencies
- `make run` - Run the application
- `make dev` - Run development server with auto-reload
- `make test` - Run tests
- `make clean` - Clean cache files
- `make mcp-inspector` - Open MCP inspector to debug tools (requires dev server running)

## API Endpoints

- `GET /` - Root endpoint
- `GET /health` - Health check
- `GET /docs` - API documentation (Swagger UI)
