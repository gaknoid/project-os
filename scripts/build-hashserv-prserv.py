#!/usr/bin/env python3

import sys
import argparse
from lib.hashserv.server import HashEquivalenceServer
from lib.prserv.server import PRServer

def main():
    parser = argparse.ArgumentParser(description="Combined Hash Equiv & PR Service")
    parser.add_argument("--bind-hash", default="unix://./hashserv.sock", help="Hash server bind address")
    parser.add_argument("--bind-pr", default="unix://./prserv.sock", help="PR server bind address")
    parser.add_argument("--database", default="./shared.db", help="Shared database path")
    args = parser.parse_args()

    # Start both servers
    hash_server = HashEquivalenceServer(args.bind_hash, args.database)
    pr_server = PRServer(args.bind_pr, args.database)

    hash_server.start()
    pr_server.start()

if __name__ == "__main__":
    main()
