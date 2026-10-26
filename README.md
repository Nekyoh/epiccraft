# EpicCraft 🛡️ — NeoForge 1.21.1

Mod de Minecraft da **Nekyoh** com novos materiais, armas com habilidades, chefes e mecânicas de combate.

## 🚀 Como rodar

Requisitos: **Java 21** (JDK) e internet na primeira vez (o Gradle baixa o Minecraft e o NeoForge).

```bash
./gradlew runClient     # abre o jogo com o mod
./gradlew build         # gera o .jar em build/libs/
```

No IntelliJ: abra a pasta do projeto, espere o Gradle sincronizar e use as configurações de execução `Client` / `Server`.

> Versões em `gradle.properties`. Se quiser atualizar o NeoForge, troque `neo_version`
> pela mais nova da linha 21.1.x em https://projects.neoforged.net/neoforged/neoforge

## ⚔️ Armas novas

| Arma | Como obter | Habilidade |
|---|---|---|
| Blaze Sword | Receita | Incendeia; botão direito solta 3 bolas de fogo igual ao Blaze |
| Creeper Sword | Receita | Golpe carregado tem 20% de chance de explodir a área (sem quebrar blocos); você leva 1 coração |
| Dark Katana | Receita | Mesmo modelo da Katana, Wither II, corte em área e defesa 50% |
| Enderman Sword | Receita | Botão direito teleporta para onde você olha (32 blocos) |
| Firework Sword | Receita | Botão direito solta foguete colorido; golpes soltam faíscas |
| Guardian Sword | Drop raro do Elder Guardian (10%) | Na mão: fôlego maior e nado mais rápido; golpes dão Cansaço + Lentidão |
| Jet (espadas de gancho) | Receita | Golpes puxam o inimigo; botão direito engancha um mob ou te puxa até um bloco |
| Kunai | Receita (4) | Arremessável, recolhe igual flecha, funciona no dispenser |
| Monarch Sword | Receita (Estrela do Nether) | Modelo grande, +1 alcance, ataque lento; golpe carregado solta onda roxa enorme (raio 5) com Sangramento II, Fraqueza e Lentidão |
| Snake Sword | Receita | Veneno II + chance de Náusea |
| Warden Sword | Drop do Warden | Golpes dão Escuridão; segure e solte o botão direito: Sonic Boom |
| Wither Rose Sword | Receita | Efeito Wither |
| Inventor Wrench | Drop do The Inventor (20%) | Chance de choque; gira blocos |
| Vampiric Dagger | Drop do Vampire King (100%) | Adaga que rouba 30% do dano como vida |
| Wrench | Receita | Dano baixo; por enquanto gira blocos (base para ferramentas futuras) |
| Umbrella | Receita | Segurando, você plana devagar sem dano de queda |

**Philosofer Stone**: drop raro do Vampire King (8% + 3% por nível de Pilhagem). Substitui o molde de melhoria de netherite na mesa de ferraria para fazer lingotes de **Nezonita** e **Tekynita**. Dá para duplicar (pedra + 7 ouro + bloco de redstone = 2 pedras).

## 👹 Mobs novos (nascem naturalmente)

- **The Inventor**
- **Vampire King**
- **King Slime**


## ⛏️ Minérios gerando no mundo

- **Nezonita** — Nether inteiro (Y 8 a 118), escondida como detritos ancestrais.
- **Tekynita** — Overworld, camadas perto da bedrock (Y −64 a −52).
- **Rubi** e **Safira** — Overworld, com versão de ardósia profunda.

## 🗡️ Combate

- **Adagas duplas**: com uma adaga em cada mão, o golpe usa a adaga mais forte + metade da soma do dano das duas (configurável). A segunda mão também balança.
- **Backstab**: adaga pelas costas = 1,5× dano.
- **Defesa estilo 1.8**: Katana, Dark Katana e Taco de beisebol defendem 50% do dano frontal segurando o botão direito (não viram escudo completo). A Frigideira continua sendo escudo de verdade.
- Configurações em `config/epiccraft-common.toml`.

## 🛡️ Bônus de armadura

- **Nezonita** completa: Força II, +6 corações, Resistência ao Fogo (só peitoral: Força I).
- **Tekynita** completa: Velocidade III, sobe 1 bloco, −80% de dano de queda, Resistência ao Fogo (só botas: Velocidade I).
- **Esmeralda** completa: Herói da Vila (nível sobe com Dádiva da Esmeralda).

Os bônus agora usam atributos, então não apagam mais poções que você bebeu.

## 🛠️ Tecnologias
- Java 21, NeoForge 21.1 (Minecraft 1.21.1), ModDevGradle
- Organização: Nekyoh
