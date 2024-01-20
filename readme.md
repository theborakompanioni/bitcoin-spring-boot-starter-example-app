
🗲 bitcoin-spring-boot-starter-example-app
===

A bitcoin-spring-boot-starter example application.

- Lightning Login (lnurl-auth)
  - uses [spring-lnurl-auth-security](https://github.com/theborakompanioni/bitcoin-spring-boot-starter/tree/master/incubator/spring-lnurl) with default login page
  - uses in-memory k1 cache (reboots invalidate previous login challenges)
- Onion Service
  - automatically registers an `.onion` url

This should be a playground for silly stuff. Only for demonstration purposes.

### Run
In order for lnurl-auth to work you must either serve your app over `https` (no self-signed cert allowed) or as Onion Service.
Currently, there is only [Simple Bitcoin Wallet][simple_bitcoin_wallet_github] that natively supports `onion` addresses (last checked 2021-07-27).
Other wallets will probably support it in the near future. Alternatively, you can use [lnpass][lnpass_homepage]
during development for testing or demonstrating the Lightning Login process.

#### Onion Service

Start the application with
```shell
./gradlew -p app bootRun --args="--spring.profiles.active=development --debug"
```

Then visit the onion url in your browser and log in with [lnpass][lnpass_homepage].

#### Clearnet

If you do not use Onion Services, serving your app with `https` during development can be done with [ngrok][ngrok_homepage]:
```shell
./ngrok http 8080
# Forwarding  https://abcdef012345.ngrok.io -> http://localhost:8080
```

Make sure to adapt the application configuration accordingly:
```yml
app:
  # use your own url here (e.g. https://myapp.ngrok.io)
  lnurl-auth-base-url: https://abcdef012345.ngrok.io
```

...or start the app with argument `app.lnurl-auth-base-url`:

Start the application with
```shell
./gradlew -p app bootRun --args="--spring.profiles.active=development --app.lnurl-auth-base-url=https://abcdef012345.ngrok.io"
```


## Development

### Requirements
- java >=21

### Build
```shell script
./gradlew build -x test
```
 
### Test
```shell script
./gradlew test integrationTest --rerun-tasks
```


## Resources

- Bitcoin: https://bitcoin.org/en/getting-started
- Spring Boot (GitHub): https://github.com/spring-projects/spring-boot
- Tor: https://www.torproject.org
---
- lnurl RFC (GitHub): https://github.com/fiatjaf/lnurl-rfc
- Wallets supporting lnurl: https://github.com/fiatjaf/lnurl-rfc#lnurl-documents
- lnpass: https://lnpass.github.io
- sqlite (GitHub): https://github.com/xerial/sqlite-jdbc
- Simple Bitcoin Wallet (GitHub): https://github.com/btcontract/wallet/
- jMolecules (GitHub): https://github.com/xmolecules/jmolecules
- sqlite (GitHub): https://github.com/sqlite/sqlite
- ngrok Website: https://ngrok.com

## License

The project is licensed under the Apache License. See [LICENSE](LICENSE) for details.

[lnpass_homepage]: https://lnpass.github.io
[simple_bitcoin_wallet_github]: https://github.com/btcontract/wallet/
[ngrok_homepage]: https://ngrok.com/
