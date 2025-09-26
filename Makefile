.PHONY: install run dev test clean mcp-inspector

install:
	uv sync

run:
	uv run python -m app.main

dev:
	uv run uvicorn app.main:app --reload --host 0.0.0.0 --port 8000

test:
	uv run pytest

clean:
	rm -rf __pycache__ app/__pycache__ app/*/__pycache__
	rm -rf .pytest_cache
	rm -rf .coverage

mcp-inspector:
	npx @modelcontextprotocol/inspector http://localhost:8000/mcp
