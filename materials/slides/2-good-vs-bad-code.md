# Good Code vs. Bad Code

---
# What makes code good or bad for you?

---
# Good code minimises costs (time, money, resources)

---
# Accidental vs Essential Complexity

---

# Essential Complexity
- **Definition**: Inherent complexity of the **problem domain**
- Cannot be removed - you must deal with it
- Comes directly from **business rules or requirements**

**Examples**:
- Banking system: interest calculation, regulatory rules
- Compiler: parsing grammar, semantic analysis

---
# Accidental (or Intentionally Added?) Complexity
- **Definition**: Complexity from the **solution/implementation**
- Not required by the problem itself
- Often caused by tools, frameworks, **poor design**, or **bored developers**

**Examples**:
- Monolithic spaghetti codebase with unclear boundaries
- Dogmatically applying the latest architectural design pattern to even the simplest problem

---
# Essential vs. Accidental Complexity
<p align="center">
  <img src="imgs/complexity.png">
</p>

---
# Key Takeaway
- **Essential complexity**: unavoidable - inherent to the problem domain 
- **Accidental complexity**: avoid it as much as possible

---
# Exercise - Accidental or Essential Complexity?

<div style="
  width: 50%;
  margin: 2em auto;
  background: #fffcc0;
  padding: 1em;
  border-radius: 0.5em;
  box-shadow: 2px 2px 6px rgba(0,0,0,0.2);
  font-size: 0.9em;
  text-align: left;
">
Using GoF design patterns in business software
</div>

---
<div style="
  width: 50%;
  margin: 2em auto;
  background: #fffcc0;
  padding: 1em;
  border-radius: 0.5em;
  box-shadow: 2px 2px 6px rgba(0,0,0,0.2);
  font-size: 0.9em;
  text-align: left;
">
Ensuring architectural integrity using tools like ArchUnit
</div>

---
<div style="
  width: 50%;
  margin: 2em auto;
  background: #fffcc0;
  padding: 1em;
  border-radius: 0.5em;
  box-shadow: 2px 2px 6px rgba(0,0,0,0.2);
  font-size: 0.9em;
  text-align: left;
">
Using Jeff's functional Java library (he's the lead developer)
</div>

---
<div style="
  width: 50%;
  margin: 2em auto;
  background: #fffcc0;
  padding: 1em;
  border-radius: 0.5em;
  box-shadow: 2px 2px 6px rgba(0,0,0,0.2);
  font-size: 0.9em;
  text-align: left;
">
Using Event Sourcing to create custom views
</div>

---
<div style="
  width: 50%;
  margin: 2em auto;
  background: #fffcc0;
  padding: 1em;
  border-radius: 0.5em;
  box-shadow: 2px 2px 6px rgba(0,0,0,0.2);
  font-size: 0.9em;
  text-align: left;
">
Backend and Frontend of the same application are developed by different teams or subteams
</div>

---
<div style="
  width: 50%;
  margin: 2em auto;
  background: #fffcc0;
  padding: 1em;
  border-radius: 0.5em;
  box-shadow: 2px 2px 6px rgba(0,0,0,0.2);
  font-size: 0.9em;
  text-align: left;
">
Having an interface for every service (e.g. CustomerService implements ICustomerService)
</div>

---
<div style="
  width: 50%;
  margin: 2em auto;
  background: #fffcc0;
  padding: 1em;
  border-radius: 0.5em;
  box-shadow: 2px 2px 6px rgba(0,0,0,0.2);
  font-size: 0.9em;
  text-align: left;
">
Every new feature is a new micro service - no need to refactor, just rewrite it.
</div>

---
<div style="
  width: 50%;
  margin: 2em auto;
  background: #fffcc0;
  padding: 1em;
  border-radius: 0.5em;
  box-shadow: 2px 2px 6px rgba(0,0,0,0.2);
  font-size: 0.9em;
  text-align: left;
">
Every person in a team has a clear role and follows the established process.
</div>
 
----
 
# Conclusion

- It always depends on the context.
- Some cases or more obvious than others, but that's what the "gap of the unknown" implies.
- It can change over time.
- We need to think in trade offs instead of absolute truths.
