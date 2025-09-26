import os

# Enable the new OpenAPI parser (FASTMCP)
os.environ["FASTMCP_EXPERIMENTAL_ENABLE_NEW_OPENAPI_PARSER"] = "true"

class Settings:
    debug: bool = True

settings = Settings()
