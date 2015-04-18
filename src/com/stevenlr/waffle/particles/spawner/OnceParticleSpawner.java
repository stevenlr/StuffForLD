/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.particles.spawner;

import com.stevenlr.waffle.particles.Particle;
import com.stevenlr.waffle.particles.ParticleSystem;

public class OnceParticleSpawner extends ParticleSpawner {

	private int _numToSpawn;
	private float _delay;
	private ParticleSystem _system;
	private int _numSpawned = 0;

	public OnceParticleSpawner(int numToSpawn, float delay, ParticleSystem system) {
		super(null);
		_numToSpawn = numToSpawn;
		_system = system;
	}

	@Override
	public void update(float dt) {
		_delay -= dt;
	}

	@Override
	public boolean canSpawnParticle() {
		return _delay <= 0 && _numSpawned < _numToSpawn;
	}

	@Override
	public boolean isDoneSpawning() {
		return _delay <= 0 && _numSpawned >= _numToSpawn;
	}

	@Override
	public Particle spawnParticle() {
		return _system.addParticle();
	}
}
