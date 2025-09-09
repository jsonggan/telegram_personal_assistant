from fastapi import FastAPI

app = FastAPI(
    title="Telegram Personal Assistant",
    description="A personal assistant bot for Telegram",
    version="0.1.0"
)

@app.get("/")
async def root():
    return {"message": "Telegram Personal Assistant API is running!"}

@app.get("/health")
async def health_check():
    return {"status": "healthy"}
