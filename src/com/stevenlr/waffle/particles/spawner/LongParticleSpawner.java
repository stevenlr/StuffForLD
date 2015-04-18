/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.particles.spawner;

import com.stevenlr.waffle.particles.Particle;
import com.stevenlr.waffle.particles.ParticleSystem;

public class LongParticleSpawner extends ParticleSpawner {

	private int _numToSpawn;
	private float _delay;
	private ParticleSystem _system;
	private float _timeSinceLastSpawned = 0;
	private int _numSpawned = 0;

	protected LongParticleSpawner(int numToSpawn, float duration, ParticleSystem system) {
		super(null);
		_numToSpawn = numToSpawn;
		_delay = duration / numToSpawn;
		_system = system;
	}

	@Override
	public boolean canSpawnParticle() {
		return _timeSinceLastSpawned >= _delay && _numSpawned < _numToSpawn;
	}

	@Override
	public boolean isDoneSpawning() {
		return _numSpawned >= _numToSpawn;
	}

	@Override
	public Particle spawnParticle() {
		_timeSinceLastSpawned = 0;
		return _system.addParticle();
	}

	@Override
	public void update(float dt) {
		_timeSinceLastSpawned += dt;
	}
}
